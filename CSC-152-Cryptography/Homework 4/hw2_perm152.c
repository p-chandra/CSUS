/*
 *==============================================================================
 * Name        : hw3_perm152.c
 * Author      : Pawan Chandra
 * 4 Digit     : 5020
 * Date        : October 7, 2019
 * Description : Program will take an unsigned char array of size 64 and passes
 * 	           : the hex to int array and then to 128bit register. The code calls
 * 			   : update function which manipulates the contents in the register
 * 	           : and then casts it back to array then unsigned char array.
 * 	           : 
 *==============================================================================
 */

#include <stdio.h>
#include <inttypes.h>
#include <string.h>
#include <emmintrin.h>

static void update();
//static uint32_t rotl();
//void psse_int();


void perm152(unsigned char *in,unsigned char *out){

	uint32_t a[16];
	memcpy(a, in, sizeof(a));

	//Load a[0] - a[3] to register
	__m128i row1 =  _mm_loadu_si128 ((__m128i *) &a[0]);
	//Load a[4] - a[7] to register
	__m128i row2 =  _mm_loadu_si128 ((__m128i *) &a[4]);
	//Load a[8] - a[11] to register
	__m128i row3 =  _mm_loadu_si128 ((__m128i *) &a[8]);
	//Load a[12] - a[15] to register
	__m128i row4 =  _mm_loadu_si128 ((__m128i *) &a[12]);

	//Run iteration 10 times
	for(int i=0; i<10; i++){
		
		//Call update method and pass by refference 
		update(&row1, &row2, &row3,  &row4);

		//Shuffle the contents in the register to align and ready for next update
		row2 = _mm_shuffle_epi32(row2, 0b00111001);//shift row 2 left 1
		row3 = _mm_shuffle_epi32(row3, 0b01001110);//shift row 3 left 2
		row4 = _mm_shuffle_epi32(row4, 0b10010011);//shift row 4 left 3

		//update after shuffle
		update(&row1, &row2, &row3,  &row4);

		//shuffle back to original
		row2 = _mm_shuffle_epi32(row2, 0b10010011);//shift row2 right 1
		row3 = _mm_shuffle_epi32(row3, 0b01001110);//shift row3 right 2
		row4 = _mm_shuffle_epi32(row4, 0b00111001);//shift row4 right 3
	}

	//Cast a[0] and store contents from row1 to it
	_mm_store_si128((__m128i *)&a[0], row1);
	//...
	_mm_store_si128((__m128i *)&a[4], row2);
	//...
	_mm_store_si128((__m128i *)&a[8], row3);
	//...
	_mm_store_si128((__m128i *)&a[12],row4);
	
	//Copy the contents of the array a to unsigned char array out
	memcpy(out, a, 64);
}

/*void psse_int(__m128i x) {
    int *p = (int *)&x;
    for (int i=0; i<1; i++) {
        printf("%08X ", p[3-i]);
    }
    	printf("\n");
}*/

static void update(__m128i* w, __m128i* x, __m128i* y, __m128i* z){

	//add register contents using sse commands
        *w = _mm_add_epi32 (*w, *x); 
	//xor "      "
	*z = _mm_xor_si128 (*z, *w);
	//Cant rotate so use sse shifts to get it to rotate
	*z = (_mm_slli_epi32 (*z, 16)) | (_mm_srli_epi32 (*z, (32 - 16)));

	*y = _mm_add_epi32 (*y, *z); 
	*x = _mm_xor_si128 (*x, *y);
	*x = (_mm_slli_epi32 (*x, 12)) | (_mm_srli_epi32 (*x, (32 - 12)));
	
	*w = _mm_add_epi32 (*w, *x); 
	*z = _mm_xor_si128 (*z, *w);
	*z = (_mm_slli_epi32 (*z, 8)) | (_mm_srli_epi32 (*z, (32 - 8)));

	*y = _mm_add_epi32 (*y, *z); 
	*x = _mm_xor_si128 (*x, *y);
	*x = (_mm_slli_epi32 (*x, 7)) | (_mm_srli_epi32 (*x, (32 - 7)));
}

//Test Case: The test case was taken from piazza from a student name Chris Stevens
//Reminder: Verify the printf works correctly using %02x 

/*
int main() {
    unsigned char res[64] = {
        0x5A,0x20,0x20,0x83,	0xFB,0x39,0x46,0x28,	0x35,0xF8,0x2B,0xB5,	0xF4,0x56,0xA6,0x01,
		0x92,0x8A,0x15,0x7C,	0xA5,0xBE,0xDE,0x5F,	0x15,0xAC,0x45,0x01,	0xC9,0xA8,0xFD,0x34,
		0x6F,0x4D,0xC4,0x3B,	0x43,0x4B,0xE8,0xC6,	0x3A,0x41,0x95,0x27,	0xF9,0x82,0xAF,0x01,
		0xB9,0x68,0x8A,0xC1,	0x9C,0xB9,0x10,0x75,	0xC9,0xDC,0x53,0x6D,	0xF1,0x4F,0x60,0x6C
		};
    
	unsigned char buf1[64], buf2[64];
    
	for (int i=0; i<64; i++){
        buf1[i] = (unsigned char)i;
	}
	perm152(buf1, buf2);
    
	if (memcmp(buf2,res,64) != 0) {
        printf("No pass\n");
    } else {
        printf("Pass\n");
    }
    return 0;
}
*/
