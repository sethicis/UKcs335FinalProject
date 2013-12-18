/*
created with obj2opengl.pl

source file    : ./court.obj
vertices       : 4
faces          : 2
normals        : 1
texture coords : 0


// include generated arrays
#import "./court.java"

// set input data to arrays
glVertexPointer(3, GL_FLOAT, 0, courtVerts);
glNormalPointer(GL_FLOAT, 0, courtNormals);

// draw data
glDrawArrays(GL_TRIANGLES, 0, courtNumVerts);
*/

public class court {

public static int courtNumVerts = 6;

public static float courtVerts [] = {
  // f 2//1 1//1 3//1 4//1
  -0.292842171330605f, -7.22588932090875e-08f, -0.499999981935277f,
  -0.297427287140294f, 0f, 0.497286190812469f,
  0.292842171330605f, 7.22588932090875e-08f, 0.500000018064723f,
  // f 2//1 1//1 3//1 4//1
  -0.292842171330605f, -7.22588932090875e-08f, -0.499999981935277f,
  0.297427287140294f, 0f, -0.497286226941916f,
  0.292842171330605f, 7.22588932090875e-08f, 0.500000018064723f,
};

public static float courtNormals [] = {
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
