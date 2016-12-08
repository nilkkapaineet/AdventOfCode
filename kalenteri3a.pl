#!/usr/local/bin/perl 

use CGI qw/:standard/;
$query = CGI::new();

&readFile;

# global index for counting possible triangles
$index = 0;

foreach $moreLines (@lines) {
	matching($moreLines);
}

####################################

sub readFile {
	open(XZY,"triangles.txt");
	push(@lines,<XZY>);
	close(XZY);	

}

####################################3

sub matching {
	my $argument = shift;
	while($argument =~ m/(\d+)(\s+)(\d+)(\s+)(\d+)/g) {
		#$n1 = $&;
		my ($n1,$n2,$n3,$n4,$n5) = ($1,$2,$3,$4,$5);
		testing($n1,$n3,$n5);
	}
}

sub testing {
	my ($n1,$n2,$n3) = @_;
	# which one is the biggest number?
	$biggest = 0;
	$b1 = 0;
	$b2 = 0;
	if ($n1 > $n2) {
		if ($n1 > $n3) {
			$biggest = $n1;
			$b1 = $n2;
			$b2 = $n3;
		} else {
			$biggest = $n3;
			$b1 = $n2;
			$b2 = $n1;
		}
	} else {
		if ($n2 > $n3) {
			$biggest = $n2;
			$b1 = $n1;
			$b2 = $n3;
		} else {
			$biggest = $n3;
			$b1 = $n2;
			$b2 = $n1;
		}
	} 
	# check if possible
	$sum = $b1 + $b2;
	if ($biggest < $sum) {
		$index += 1;
		print "This is $index possible: $biggest $b1 $b2 \n";
	}
}