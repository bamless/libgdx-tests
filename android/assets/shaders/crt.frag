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

const float mask = 0.45;
const int br = 0;
const float contrast = 0.0;

void main() { 
	vec4 color =  texture2D(u_texture, v_texCoords);
	vec4 outColor = vec4(0.0, 0.0, 0.0, 1.0);
	
	color = clamp(color + float(br)/255.0, 0.0, 1.0);
	
	color = color - contrast * (color - 1.0) * color *(color - 0.5);

	int pp = int(mod(float(gl_FragCoord.x), 3.0));
	if(pp == 1) {
		outColor.r = color.r;
		outColor.g = color.g * mask;
		outColor.b = color.b * mask;
	} else if(pp == 2) {
		outColor.r = color.r * mask;
		outColor.g = color.g;
		outColor.b = color.b * mask;
	} else {
		outColor.r = color.r * mask;
		outColor.g = color.g * mask;
		outColor.b = color.b;
	}
	
	if( int(mod(float(gl_FragCoord.y), 3.0)) == 0) {
		outColor *= 0.7;
	}
	
	gl_FragColor = outColor;
}
