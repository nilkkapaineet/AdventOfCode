#!/usr/local/bin/perl 

use CGI qw/:standard/;
$query = CGI::new();

&matching;


####################################3

sub matching {
	$rivi = "L1, L3, L5, L3, R1, L4, L5, R1, R3, L5, R1, L3, L2, L3, R2, R2, L3, L3, R1, L2, R1, L3, L2, R4, R2, L5, R4, L5, R4, L2, R3, L2, R4, R1, L5, L4, R1, L2, R3, R1, R2, L4, R1, L2, R3, L2, L3, R5, L192, R4, L5, R4, L1, R4, L4, R2, L5, R45, L2, L5, R4, R5, L3, R5, R77, R2, R5, L5, R1, R4, L4, L4, R2, L4, L1, R191, R1, L1, L2, L2, L4, L3, R1, L3, R1, R5, R3, L1, L4, L2, L3, L1, L1, R5, L4, R1, L3, R1, L2, R1, R4, R5, L4, L2, R4, R5, L1, L2, R3, L4, R2, R2, R3, L2, L3, L5, R3, R1, L4, L3, R4, R2, R2, R2, R1, L4, R4, R1, R2, R1, L2, L2, R4, L1, L2, R3, L3, L5, L4, R4, L3, L1, L5, L3, L5, R5, L5, L4, L2, R1, L2, L4, L2, L4, L1, R4, R4, R5, R1, L4, R2, L4, L2, L4, R2, L4, L1, L2, R1, R4, R3, R2, R2, R5, L1, L2";
	$x = 0;
	$y = 0;
	$direction = 0;
	# 0=N, 1=E, 2=S, 3=W
	while($rivi =~ m/(L|R)/g) {
		print "Matched: $&\n";
		if ($& eq L) {
			# direction to left
			if ($direction == 0) { $direction = 3; }
			elsif ($direction == 1) { $direction = 0; }
			elsif ($direction == 2) { $direction = 1; }
			else { $direction = 2; }
		} else { #  case R
			# direction to right
			if ($direction == 0) { $direction = 1; }
			elsif ($direction == 1) { $direction = 2; }
			elsif ($direction == 2) { $direction = 3; }
			else { $direction = 0; }
		}
		# get number next
		$rivi =~ m/(\d+)/g;
		print "Direction: $direction \n";
		print "Number: $&\n";
		# check if x or y coordinates is changing
		if ($direction == 0) { 
			# adding y-coordinate
			$y += $&;
		} elsif ($direction == 1) {
			# adding x-coordinate
			$x += $&;
		} elsif ($direction == 2) {
			# subtracting y-coordinate
			$y -= $&;
		} else {
			# subtracting x-coordinate
			$x -= $&;
		}				
		print "Position: $x , $y \n";
	}
	print "Final position: $x,$y \n";
	$distance = abs($x)+abs($y);
	print "Distance from starting point: $distance \n";
}
