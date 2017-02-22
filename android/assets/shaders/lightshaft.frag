#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

//"in" attributes from our vertex shader
varying LOWP vec4 v_color;
varying vec2 v_texCoords;

//declare uniforms
uniform sampler2D u_texture;
uniform vec4 color;

void main() {
	vec4 sample = texture2D(u_texture, v_texCoords);
    gl_FragColor = vec4(color.rgb, sample.a) * sample;
}
