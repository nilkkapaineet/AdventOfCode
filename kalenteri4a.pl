#!/usr/local/bin/perl 

$r[0] = "bkwzkqsxq-tovvilokx-nozvyiwoxd-172[okvxi]";
$r[1] = "wifilzof-wbiwifuny-yhachyylcha-526[qrazx]";
$r[2] = "jvyyvzpcl-jhukf-shivyhavyf-487[vyhfj]";

my @CalArray;
open(my $fh, "<", "kalenteri4aTiedot.txt")
    or die "Failed to open file: $!\n";
while(<$fh>) { 
    chomp; 
    push @CalArray, $_;
} 
close $fh;

$totalCounter = 0;
$numberToRemember = 0;

foreach my $line (@CalArray) {
	$commonLettersFound = "";
	$retval = &commonLetter($line);
#	print "Found these: $commonLettersFound \n";
	$retval = &removeCommon($retval);
	$retval = &findNumber($retval);
	$retval = &charCounter($retval);
	$commonLettersFound =~ s/[\W\d_]//g; # remove all non-word characters and digits and underscores
	$retval =~  s/[\W\d_]//g; # remove all non-word characters and digits and underscores
	if ($commonLettersFound eq $retval) {
		$totalCounter += $numberToRemember;
	}
	print "Total: $totalCounter \n";
}

sub charCounter {
	my %count;
	my ($vari) = @_; 
	$vari =~ tr/-//d;
	foreach my $str (split//, $vari) {
		$count{$str}++;
	}

	my $firstFive = "";
	my $ffCounter = 0;
	foreach my $f1 (sort { $count{$b} <=> $count{$a} or $a cmp $b } keys %count) {
	#    	print "\n", $f1, $count{$f1};
		if ($ffCounter < 5) {
			$firstFive = $firstFive . $f1;
			$ffCounter += 1;
		}
	}
	return $firstFive;
}

sub commonLetter {
	# search presumed most common letters
	my ($vari) = @_; 
	$vari =~ m/\[(.+?)\]/;
	#$r[0] =~ m/\[(.+?)\]/;	
	$commonLetters = $&;
	# print "found: $& \n";
	# get them into variables
	chop($commonLetters);
	$l1 = chop($commonLetters);
	$l2 = chop($commonLetters);
	$l3 = chop($commonLetters);
	$l4 = chop($commonLetters);
	$l5 = chop($commonLetters);
	# print "Letters: $l1 $l2 $l3 $l4 $l5 \n";
	$commonLettersFound = $commonLetterFound . $l5 . $l4 . $l3 . $l2 . $l1;
	return $vari;
}

sub removeCommon {
	# remove common letters regex
	my ($vari) = @_; 
	$vari =~ s/\[(.+?)\]//;
	#$r[0] =~ s/\[(.+?)\]//;
	# print "$vari \n";
	return $vari;
}

sub findNumber {
	# find number
	my ($vari) = @_;
	$vari =~ m/(\d+)/g;
	#$r[0] =~ m/(\d+)/g;
	$number = $&;
	# print "$number \n";
	$numberToRemember = $number;

	#remove number
	#$r[0] =~ s/(\d+)//g;
	$vari =~ s/(\d+)//g;
	return $vari;
}

