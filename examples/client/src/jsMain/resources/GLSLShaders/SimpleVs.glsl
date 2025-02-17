attribute vec3 aSquareVertexPosition;
uniform mat4 uViewProjTransform;
uniform mat4 uModelTransform;
    void main(void) {
        gl_Position = uViewProjTransform * uModelTransform * vec4(aSquareVertexPosition, 1.0);
}