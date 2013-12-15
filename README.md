INITIAL Git README
==================
Description:
This project is made by Michael Denny and Kyle Blagg.

The goal of this Computer Science Graphics Class final project is to create
an android app that simulates a simple basketball game using the
accelerometer on the android device.

Updates (Change log)
================== 

V 0.0.2: added objloader, test file for basketball goal model.
Found objloader program and modified it to output .java class files that can be loaded
into the program as the vertices and normals.

PROBLEM: The objloader program can generate class files that are too large for the program
to handle.  The maximum file size limit on java is 64 Kbs, and some object files
depending on complexity, can be around 484 KBs.

Not sure if the objloader can be resolved.  We may have to use a work around
where we export the blender made models as independent objects then load those with
the objloader and assemble the object in openGL.

V 0.0.1: made a README.md file. Woot.
