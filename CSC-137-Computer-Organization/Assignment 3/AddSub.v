// Pawan Chandra
// AddSub.v, 137 Verilog Programming Assignment #3


module TestMod;
	parameter STDIN = 32'h8000_0000;
	
   reg [7:0] str [1:3]; // typing in 2 chars at a time (decimal # and Enter key)
   reg [4:0] X, Y;      // 5-bit X, Y to sum
   wire [4:0] S;        // 5-bit Sum to see as result
   wire C5;             // like to know this as well from result of Adder
   reg C0;
   wire E;
   wire C4;
	
	
	BigAdder bigAdder(X, Y, C0, S, C5, E, C4);
	
	initial begin
	  $display("Enter X (range 00 ~ 15): ");
      str[1] = $fgetc(STDIN); // 1st 
      str[2] = $fgetc(STDIN); // 2nd 
      str[3] = $fgetc(STDIN); // input
	  
      X = (str[1] - 48) * 10 + (str[2] - 48);
	  
      $display("Enter Y (range 00 ~ 15): ");
      str[1] = $fgetc(STDIN); // 1st 
      str[2] = $fgetc(STDIN); // 2nd
      str[3] = $fgetc(STDIN); // input
	  
      Y = (str[1] - 48) * 10 + (str[2] - 48);
	  
	  $display("Enter either '+' or '-': ");
      if ($fgetc(STDIN) == "+") 
         C0 = 0;
      else
         C0 = 1;
	  
	  #1;
      $display("X =%b (%d) Y =%b (%d) C0=%b", X, X, Y, Y, C0);
      $display("Result=%b (as unsigned %d)", S, S);
      $display("C4 =%b C5 =%b E =%b", C4, C5, E);
	
	if(E == 0)
		$display("Since E is 0, C5 is not needed");
	else
		$display("Since E is 1, correct with C5 in front: 0%b",S);
    	end
endmodule

module BigAdder(X, Y, C0, S, C5, E, C4);
   input [4:0] X, Y;
   input C0;
   output [4:0] S;     
   output C5;
   output E;
   output C4;
   
   wire [0:5] w;  // Temp wires
   wire [0:4] z;  // Temp wires

   assign  w[0] = C0;
   assign  C4 = w[4];
   assign  C5 = w[5];

   xor(z[0], Y[0], w[0]);
   xor(z[1], Y[1], w[0]);
   xor(z[2], Y[2], w[0]);
   xor(z[3], Y[3], w[0]);
   xor(z[4], Y[4], w[0]);
   
   FullAdderMod FA0(X[0], z[0], w[0], S[0], w[1]);
   FullAdderMod FA1(X[1], z[1], w[1], S[1], w[2]);
   FullAdderMod FA2(X[2], z[2], w[2], S[2], w[3]);
   FullAdderMod FA3(X[3], z[3], w[3], S[3], w[4]);
   FullAdderMod FA4(X[4], z[4], w[4], S[4], w[5]);
   
   xor(E, w[4], w[5]);
  endmodule
  
  module FullAdderMod(X, Y, Cin, S, Cout); // single-bit adder module
    input X, Y, Cin;
    output S, Cout;
	
    wire AND0, AND1, XOR0;
	
    and(AND0, X, Y);
    and(AND1, Cin, XOR0);
    xor(XOR0, X, Y);
	
    xor(S, XOR0, Cin); 
    or(Cout, AND1, AND0); 
	
 endmodule
