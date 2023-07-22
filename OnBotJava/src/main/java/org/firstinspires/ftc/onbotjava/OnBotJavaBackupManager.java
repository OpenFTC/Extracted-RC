/*
 * Copyright (c) 2023 Michael Hoogasian
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of Michael Hoogasian nor the names of his contributors may be used to
 * endorse or promote products derived from this software without specific prior
 * written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.firstinspires.ftc.onbotjava;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

class OnBotJavaBackupManager
{
    protected static final int NUM_SRC_BACKUPS_TO_KEEP = 35;
    protected static final int NUM_INDEX_DIGITS = 8; // so we never overflow lol
    protected static final int INDEX_INVALID = -1;
    protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd+HH.mm.ss", Locale.US);
    protected static final String BACKUP_NAME_PREFIX = "OnBot-build-";
    protected static final String TAG = "OnBotJavaBackupManager";

    protected static int parseBackupNumber(String filename)
    {
        // If it doesn't end with .zip it's definitely not a backup
        if (!filename.endsWith(".zip"))
        {
            return INDEX_INVALID;
        }

        // If it doesn't start with our prefix, it's a nope
        if (!filename.startsWith(BACKUP_NAME_PREFIX))
        {
            return INDEX_INVALID;
        }

        // If the filename minus the extension is less than the number of index digits,
        // plus the length of the prefix, it's definitely not a backup.
        if (filename.replace(".zip", "").length() < NUM_INDEX_DIGITS+BACKUP_NAME_PREFIX.length())
        {
            return INDEX_INVALID;
        }

        try
        {
            // Now try to parse the first N digits of the filename as an integer
            String indexSubstring = filename.substring(BACKUP_NAME_PREFIX.length(), BACKUP_NAME_PREFIX.length()+NUM_INDEX_DIGITS);
            int i = Integer.parseInt(indexSubstring);

            // If it's less than zero it's not a backup
            if (i < 0)
            {
                return INDEX_INVALID;
            }
            // If it's >= zero, we can be reasonably sure this is a backup file
            else
            {
                return i;
            }
        }
        catch (Exception e)
        {
            // If we can't parse an integer out of the beginning, this is NOT a backup lol
            return INDEX_INVALID;
        }
    }

    protected static ArrayList<File> getSortedBackupList()
    {
        ArrayList<File> backups = new ArrayList<>();

        // List all the files in the backup directory
        File[] allFiles = OnBotJavaManager.srcBackupDir.listFiles();

        if (allFiles != null)
        {
            for (File f : allFiles)
            {
                // If it's not a file, it can't be a backup
                if (!f.isFile()) continue;

                // If our name parser is convinced this is a backup, then go
                // ahead and add it to our running list of backups
                if (parseBackupNumber(f.getName()) != INDEX_INVALID)
                {
                    backups.add(f);
                }
            }
        }

        // Now sort the list of backups in oldest to newest order based on
        // the backup index number prefix
        backups.sort(new Comparator<File>()
        {
            @Override
            public int compare(File f1, File f2)
            {
                // This should never fail bc the file only ever made it into the list in
                // the first place if it previously passed the parsing step
                int i1 = parseBackupNumber(f1.getName());
                int i2 = parseBackupNumber(f2.getName());

                if (i1 == i2)
                {
                    return 0; // Should never happen lol
                }

                return i1 > i2 ? 1 : -1;
            }
        });

        return backups;
    }

    protected static void archiveSourceForBuild()
    {
        // Get a list of previous build backups, sorted oldest to newest
        ArrayList<File> sortedBackups = getSortedBackupList();

        // If we're at capacity, go ahead and trim the oldest backup before we write the new one
        if (sortedBackups.size() >= NUM_SRC_BACKUPS_TO_KEEP)
        {
            Log.d(TAG, String.format("Trimming old build backup: %s", sortedBackups.get(0).getAbsolutePath()));
            sortedBackups.get(0).delete();
            sortedBackups.remove(0);
        }

        // Now we need to figure out what index to use for the this build backup
        int nextIndex;
        if (sortedBackups.size() > 0)
        {
            // This should never fail bc the file only ever made it into the list in the first place
            // if it previously passed the parsing step
            nextIndex = parseBackupNumber(sortedBackups.get(sortedBackups.size()-1).getName())+1;
        }
        else
        {
            nextIndex = 0;
        }

        // Why does java not support wildcard field widths that's so silly
        String fileName = String.format(Locale.US, "%s%0" + NUM_INDEX_DIGITS + "d-%s.zip", BACKUP_NAME_PREFIX, nextIndex, DATE_FORMAT.format(new Date()));
        File file = new File(OnBotJavaManager.srcBackupDir, fileName);
        Log.d(TAG, String.format("Backing up source code for this build to: %s", file.getAbsolutePath()));

        // Actually do the backup :)
        try
        {
            OnBotJavaFileSystemUtils.createZipFileFrom(OnBotJavaManager.srcDir, file);
            Log.d(TAG, "Finished backing up source code for this build");
        }
        catch (IOException e)
        {
            Log.d(TAG, "FAILED to backup source code for this build");
        }
    }
}
