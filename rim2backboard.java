/*
created with obj2opengl.pl

source file    : ./rim2backboard.obj
vertices       : 4
faces          : 2
normals        : 1
texture coords : 0


// include generated arrays
#import "./rim2backboard.java"

// set input data to arrays
glVertexPointer(3, GL_FLOAT, 0, rim2backboardVerts);
glNormalPointer(GL_FLOAT, 0, rim2backboardNormals);

// draw data
glDrawArrays(GL_TRIANGLES, 0, rim2backboardNumVerts);
*/

public class rim2backboard {

public static int rim2backboardNumVerts = 6;

public static float rim2backboardVerts [] = {
  // f 2//1 1//1 3//1 4//1
  0.5f, 0f, -0.487045934492682f,
  -0.5f, 0f, -0.487045934492682f,
  -0.5f, 0f, 0.487045934492682f,
  // f 2//1 1//1 3//1 4//1
  0.5f, 0f, -0.487045934492682f,
  0.5f, 0f, 0.487045934492682f,
  -0.5f, 0f, 0.487045934492682f,
};

public static float rim2backboardNormals [] = {
  // f 2//1 1//1 3//1 4//1
  0f, 1f, 0f,
  0f, 1f, 0f,
  0f, 1f, 0f,
  // f 2//1 1//1 3//1 4//1
  0f, 1f, 0f,
  0f, 1f, 0f,
  0f, 1f, 0f,
};

}
