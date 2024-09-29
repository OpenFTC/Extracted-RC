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

Neither the name of FIRST nor the names of its contributors may be used to
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

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/*
 * Suppressing the warning to use AppCompat because AppCompat breaks stubJars
 * and the author does not know how to fix stubJars so that it will build.
 */
@SuppressLint("AppCompatCustomView")
public class IpAddressEditText extends EditText {

    private static final String DOT = ".";
    private int currentNumberIndex = 0;

    public IpAddressEditText(Context context) {
        super(context);
        init();
    }

    public IpAddressEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IpAddressEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Set input type to number
        // setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        // Add text watcher to automatically insert dots
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action required
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action required
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Insert dots after every third digit
                String text = s.toString();
                if (!text.isEmpty() && text.length() % 3 == 0) {
                    if (!text.endsWith(DOT)) {
                        s.insert(text.length(), DOT);
                        currentNumberIndex++;
                    }
                }
            }
        });

        // Limit the maximum length to 15 characters (4 numbers + 3 dots)
        // setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        // Handle arrow key events
        /*
         * The following causes the edit text to be uneditable.
         * 
        setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        // Move to the next number field
                        moveCursorToNextNumber();
                        return true;
                    }
                }
                return false;
            }
        });
         */
    }

    private void moveCursorToNextNumber() {
        int selectionStart = getSelectionStart();
        if (selectionStart == getText().length()) {
            // Already at the end, nothing to do
            return;
        }
        if (currentNumberIndex < 4 && selectionStart % 3 == 2) {
            // Move to the next number
            setSelection(selectionStart + 1);
            currentNumberIndex++;
        }
    }

    public String getIpAddress() {
        String text = getText().toString();
        // Remove trailing dot if it exists
        if (text.endsWith(DOT)) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }
}
