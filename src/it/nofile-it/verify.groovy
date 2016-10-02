File file1 = new File( basedir, 'target/file1' );

assert file1.isFile()
assert file1.readLines() == ['line 1', 'line 2']

File file2 = new File( basedir, 'target/file2' );

assert file2.isFile()
assert file2.readLines() == ['A simple IT verifying the basic use case.', 'value1']