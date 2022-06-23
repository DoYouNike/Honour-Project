package com.example.letmeeat;
// create class to represented vector 3
public class Vector3 {
    float x, y, z;

        Vector3(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector3 add(Vector3 vector) {
            x += vector.x;
            y += vector.y;
            z += vector.z;
            return this;
        }


}
