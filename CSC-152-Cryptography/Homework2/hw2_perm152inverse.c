/*
 *===============================================================================
 * Name        : hw2_perm152inverse.c
 * Author      : Pawan Chandra
 * 4 Digit     : 5020
 * Date        : September 20, 2019
 * Description : The code below takes an unsigned char array of size 64 and performs
 * 			   : bit toggling. The steps the code uses to toggle follows the AXR
 * 			   : layout. This time we are inversing the array to get the original 
 * 			   : char array. 
 *================================================================================
 */

#include <stdio.h>
#include <inttypes.h>
#include <string.h>

//Method Decleration
static void update();
static uint32_t rotl();

/*
*  perm152inverse method takes an char array and uses the inverse of 
*  of update method to get back the original array. Dr. Krovetz "In other words, if a, b, 
*  and c are all 64-byte buffers, then perm152(a,b) followed by
*  perm152inverse(b,c) should always result in buffers a and c having identical contents."
*/
void perm152inverse(unsigned char *in, unsigned char *out){

	//create unsigned 32bit array size of 16
	uint32_t a[16];
	//Use memcpy to tranfer the contents of in to array a
	memcpy(a, in, sizeof(a));	
	
	//The update method is in different order, starting from the bottom of the diagram
	//to the top
	for(int i=0; i<10; i++){
		update(&a[0], &a[5], &a[10], &a[15]);
		update(&a[1], &a[6], &a[11], &a[12]);
		update(&a[2], &a[7], &a[8],  &a[13]);
		update(&a[3], &a[4], &a[9],  &a[14]);
		
		//These 4 update methods were originally executed first
		update(&a[0], &a[4], &a[8],  &a[12]);
		update(&a[1], &a[5], &a[9],  &a[13]);
		update(&a[2], &a[6], &a[10], &a[14]);
		update(&a[3], &a[7], &a[11], &a[15]);
	}

	//Copy the contents of char array a to the output array out
	memcpy(out, a, 64);
}

/*
* Following the AXR method backwards and starting from the bottom of the diagram 
* we have a inv.Rotation, then inv.XOR, and finally an inv.And. 
*/
static void update(unsigned int* w, unsigned int* x, unsigned int* y, unsigned int* z){
	*x = rotl(*x, 7);  //rotate right
	*x = *y ^ *x; 	   //inverse of XOR is not XNOR... its XOR
	*y = *y - *z;      // Subtraction
	*z = rotl(*z, 8);  *z = *z ^ *w; *w = *w - *x;
	*x = rotl(*x, 12); *x = *x ^ *y; *y = *y - *z;
	*z = rotl(*z, 16); *z = *z ^ *w; *w = *w - *x;
}

//Right rotation of the given amount in update method.
static uint32_t rotl(unsigned int temp, int shift){
	return temp = (temp >> shift) | (temp << (32-shift));
}


//Future Reminder: Confusing due to the printf. Printf will display the hex text in reverse order.
//Test Code: Test code is the hex code produced by the output of hw2_perm152.c code 
/*
int main(){

	unsigned char in[64] ={
        0x83, 0x9B, 0x44, 0xE3,  0x83, 0x9B, 0x44, 0xE3,  0x83, 0x9B, 0x44, 0xE3,  0x83, 0x9B, 0x44, 0xE3,
        0x8F, 0x63, 0xC9, 0xA5,  0x8F, 0x63, 0xC9, 0xA5,  0x8F, 0x63, 0xC9, 0xA5,  0x8F, 0x63, 0xC9, 0xA5,
        0x15, 0x5D, 0xED, 0x72,  0x15, 0x5D, 0xED, 0x72,  0x15, 0x5D, 0xED, 0x72,  0x15, 0x5D, 0xED, 0x72,
        0xE5, 0xAA, 0x01, 0x3F,  0xE5, 0xAA, 0x01, 0x3F,  0xE5, 0xAA, 0x01, 0x3F,  0xE5, 0xAA, 0x01, 0x3F
		};

	unsigned char out[64];
	
	perm152inverse(in,out);

	int count = 0;
	for(int i=0; i<64; i++){
		printf("%02x",out[i]);
		count++;
		if(count % 4 == 0){
			printf("\n");
		}
	}
 
}
*/
