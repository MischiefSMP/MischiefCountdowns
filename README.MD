# MischiefCountdowns

MischiefCountdowns allows you to create multiple countdowns that can be displayed both via a boss bar and in chat (disabled by default).

![Demo](https://i.imgur.com/VkvgwWi.png)

Command Usage:
```/countdown [start/stop] <id> <seconds> [pink/blue/red/green/yellow/purple/white]```

Commands exist with a unique id. This id can contain Colors (which are only displayed).

So the id ```&6Morning&4Meeting``` becomes ```morningmeeting``` in practice.

This is only important should you want to stop a countdown.

The config.yml allows you to change multiple settings like default bar color, if its shown at all etc.

Note: 
Should you get an error while updating to a newer version please try to delete the Plugin folder "MischiefCountdowns" and restart.

## PlaceholderAPI

MischiefCountdowns adds two placeholders:

%mischiefcountdowns_id_mycountdown% -> Translates into the formatted id, so with color and upper/lowercase letters

%mischiefcountdowns_number_mycountdown% -> Translates into the actual countdown as an Integer

License
----

MIT License

Copyright (c) 2023 MischiefSMP.com

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
