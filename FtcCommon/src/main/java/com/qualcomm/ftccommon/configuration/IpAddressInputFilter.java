/*
Copyright (c) 2024 FIRST

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.qualcomm.ftccommon.configuration;

import android.text.InputFilter;
import android.text.Spanned;

public class IpAddressInputFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        // Allow digits, dots, and backspace
        for (int i = start; i < end; i++) {
            char character = source.charAt(i);
            if (!Character.isDigit(character) && character != '.' && character != '\b') {
                return ""; // Block invalid characters
            }
        }

        String newString = dest.toString() + source.toString();
        // Don't allow two dots in a row.
        if (newString.contains("..")) {
            return "";
        }
        // Check for more than 3 dots and limit octets to 3 digits
        String arr[] = newString.split("\\.");
        if (arr.length > 4) {
            return "";
        }
        if ((arr.length == 4) && (newString.charAt(newString.length() - 1) == '.')) {
            return "";
        }

        String[] octets = newString.split("\\.");
        for (String octet : octets) {
            if (octet.length() > 3) {
                return "";
            }
        }

        return null; // Allow the input
    }
}