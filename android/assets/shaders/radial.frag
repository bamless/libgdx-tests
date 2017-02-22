#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;
//The center (in screen coordinates) of he blur
uniform vec2 cent;

//these things should be uniforms but nonmeva'
const float blurStart = 1.0; /// blur offset
const float blurWidth = -0.85; ///how big it should be
const int nsamples = 100;

void main() {
	vec2 tc = v_texCoords;
	tc -= cent;
	
	//return vector
	vec4 c = vec4(0.0, 0.0, 0.0, 0.0);
	
	for(int i=0; i < nsamples; i++) {
    	float scale = blurStart + blurWidth * (float(i) / (float(nsamples -1)));
    	c += texture2D(u_texture, tc * scale + cent);
   	}
	
	gl_FragColor = c/float(nsamples);
}
