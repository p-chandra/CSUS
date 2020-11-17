/*
 *==============================================================================
 * Name        : hw4_perm152hash.c
 * Author      : Pawan Chandra
 * 4 Digit     : 5020
 * Date        : October 31, 2019
 * Description : The program
 * 	           : Implement a sponge-based cryptographic hash using Perm152 from
 * 			   : the previous assignment. The function perm152hash process 32
 * 	           : bytes at a time and XOR with block. The remaining bytes(tail)
 * 	           : is also XOR'd and padded to the block.
 * Compile     : gcc hw2_perm152.c hw4_perm152hash.c -o output  
 * Run         :./output
 *==============================================================================
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void perm152(unsigned char *in, unsigned char *out); // each an array of 64 bytes

void perm152hash(unsigned char *m, int mbytes, unsigned char *res){
	//Let r = rate and c = capacity and r + c = length of Perm (all in bytes).
	//r + c => 32 + 32 = 64 length of Perm
	//Capacity c, rate r, and output length b should all be 32 bytes.
	unsigned char b[64];
	for(int i=0; i<64; i++){
		b[i]=(unsigned char)i+1;
	}
	
	//Take 32 bytes at a time and xor it to the block
	while(mbytes >= 32){
		for(int i=0; i<32; i++){
			b[i] = b[i] ^ m[i];
		}
		//Pass block to perm152
		perm152(b, b);
		//Decrement the length of bytes by 32 until you reach the tail
		mbytes = mbytes - 32;
		//Keep track of size	
		m = m + 32;
	}
	//Take the remaining bytes and xor it with the block.
	//Getting it ready for padding.
	for(int i=0; i<mbytes; i++){
		b[i] =b[i] ^ m[i];
	}
	//Pad the difference (mbytes) with 1000 0000
	//Thank you professor for this shortcut (0x80).
	b[mbytes] = b[mbytes] ^ 0x80;
	perm152(b,b);
	memcpy(res, b, 32);
}
/*
int main() {
    unsigned char res0[] =  {0xAE,0x4F,0x70,0x57,0x1A,0xEC,0xF4,0x5C,0x35,0x95,0x1D,
                             0x34,0xC5,0xD5,0x47,0x65,0xC8,0x9A,0x24,0x7C,0x7A,0x5F,
                             0xE6,0x42,0x6B,0x23,0x4A,0x22,0x2F,0xB4,0x11,0xAC};
    unsigned char res1[] =  {0x20,0x95,0x72,0xD5,0x3D,0x19,0x15,0xA9,0x66,0x69,0xE0,
                             0x83,0x19,0xDF,0x53,0x52,0x10,0x67,0xF6,0xFC,0x29,0xDD,
                             0xC5,0x35,0x3B,0x09,0x15,0xB9,0x48,0x01,0xA8,0xCA};
    unsigned char res31[] = {0x16,0x1C,0x24,0x2D,0x88,0x13,0xA9,0xF7,0xD2,0x6D,0xF1,
                             0xAA,0xCA,0xD8,0x44,0x3A,0xC6,0xBF,0x5B,0x15,0x96,0x98,
                             0x17,0xA8,0xC0,0xF4,0x57,0xDF,0x13,0xD6,0xCD,0xC6};
    unsigned char res32[] = {0x83,0x9E,0xD5,0x38,0xD1,0x8B,0x7C,0x12,0xE0,0x47,0x1A,
                             0x2E,0xAB,0xB9,0xBE,0x9F,0xE8,0x9F,0x79,0x1B,0xDE,0xFE,
                             0x2E,0x96,0x44,0x44,0x19,0x82,0xA9,0x85,0xDB,0x94};
    unsigned char res33[] = {0xD0,0x05,0x85,0xF2,0x35,0xBC,0x38,0x46,0x34,0x57,0x27,
                             0xDD,0x0C,0x08,0x78,0x67,0x1D,0x0F,0x5E,0x8A,0xFB,0x9D,
                             0x3C,0xE8,0x9E,0x18,0x94,0x9A,0x7D,0x87,0x93,0x9C};
    unsigned char res1000000[] = {0x80,0x3E,0xAB,0x74,0x12,0x71,0x4F,0x12,0x3D,0x0E,
                                  0x0B,0x9B,0x83,0x55,0x9C,0x81,0xB5,0x6B,0x2F,0x12,
                                  0x67,0xE3,0x6F,0xC5,0xF4,0xC4,0x51,0x4C,0xFF,0x9C,
                                  0x4E,0x95};
    unsigned char buf[32];
    unsigned char *pt = malloc(1000000); // 1 million bytes
    int pass = 1;
    
    for (int i=0; i<1000000; i++)
        pt[i] = (unsigned char)((i+1)*(i+3)%251);
    
    perm152hash(pt+1000000, 0, buf);
    pass = pass && (memcmp(buf,res0,32)==0);
    
    perm152hash(pt+1000000-1, 1, buf);
    pass = pass && (memcmp(buf,res1,32)==0);
    
    perm152hash(pt+1000000-31, 31, buf);
    pass = pass && (memcmp(buf,res31,32)==0);
    
    perm152hash(pt+1000000-32, 32, buf);
    pass = pass && (memcmp(buf,res32,32)==0);
    
    perm152hash(pt+1000000-33, 33, buf);
    pass = pass && (memcmp(buf,res33,32)==0);
    
    perm152hash(pt, 1000000, buf);
    pass = pass && (memcmp(buf,res1000000,32)==0);
    
    printf("%s\n", (pass ? "Pass" : "No pass"));
    free(pt);
    return 0;
}
/*
 Let r = rate and c = capacity and r + c = length of Perm (all in bytes).
 Let b = number of bytes of output desired (assumed <= 2*r)
 Mpadded = M || 10* to multiple of r.
 Let M1 || M2 || ... || Mn = Mpadded with each Mi being r bytes long
 Let block = 0x01 || 0x02 || ... || (unsigned char)(r+c)
 for i = 1 to n
 xor Mi into the first r bytes of block
 block = Perm(block)
 output the first min(r,b) bytes of block
 if (b > r)
 block = Perm(block)
 output first (b-r) bytes of block 
*/