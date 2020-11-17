module main(input clk, rst, x, output reg z);
   
   reg [2:0] state;
   parameter S0 = 3'b000,
             S1 = 3'b001,
             S2 = 3'b010,
             S3 = 3'b011,
             S4 = 3'b100;
			
   always @( posedge clk, posedge rst )
   begin
   if( rst )
       state <= S0;
   else
   begin
       case( state )
       
	   S0:
       begin
            if( x == 1'b1 ) begin state <= S1; z <= 0; end
            else begin state <= S0; z <= 0; end
       end

       S1:
       begin
            if( x == 1'b0 ) begin state <= S2; z <= 0; end
            else begin state <= S1; z <= 0; end
       end

       S2:
       begin
            if( x == 1'b1 ) begin state <= S3; z <= 0; end
            else begin state <= S0; z <= 0; end
       end

       S3:
       begin
            if( x == 1'b0 ) begin state <= S4; z <=1; end
            else begin state <= S1; z <= 0; end
       end
	   
	   S4:
       begin
            if( x == 1'b0 ) begin state <= S0; z<=0; end
            else begin state <= S3; z<=0; end
       end
	   
       endcase
   end
end

endmodule

module main_test;
 reg  clk, rst, x;
 wire z;
 integer i;
 
 main dut( clk, rst, x, z);

initial
 begin
   clk = 0;
   rst = 1;
   #5 rst = 0;
   
   for( i = 0; i <= 30; i = i + 1)
   begin
      x = $random % 2;
      #2 clk = 1;
      #2 clk = 0;
      $display("State = ", dut.state, " Input = ", x, ", Output = ", z);
   end
 end
endmodule