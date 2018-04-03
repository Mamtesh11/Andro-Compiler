<?php

$aCode = $_POST['code'];

$fInput = $_POST['inputs'];

$fLanguage = $_POST['language'];

$date = time();

if($fLanguage == 'C'){
	$input_file = "inputc.txt";

	file_put_contents($input_file, $fInput);

	$c = "C";
	$file = $c.$date;
	$my_file = $file.'.c';
	

	file_put_contents($my_file, $aCode);
 
	system("gcc {$my_file} 2> errorc.txt");
 
	$error = file_get_contents("errorc.txt");

	if($error==''){
    		system("./a.out < inputc.txt > outputc.txt");
    		$output = file_get_contents("outputc.txt");
	}
	else
    		$output = $error;
}
else if($fLanguage == 'C++'){

	$input_file = "input.txt";

	file_put_contents($input_file, $fInput);

	$c = "C++";
	$file = $c.$date;
	$my_file = $file.'.cpp';

	file_put_contents($my_file, $aCode);
 
	system("g++ {$my_file} 2> error1.txt");
 
	$error = file_get_contents("error1.txt");

	if($error==''){
    		system("./a.out < input.txt > output.txt");
    		$output = file_get_contents("output.txt");
	}
	else
    		$output = $error;
}

	
echo json_encode($output);

?>
