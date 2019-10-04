File file1 = new File( basedir, 'src/path/to/file1.txt' );

assert file1.isFile()
def lines = file1.readLines()
assert lines.size() == 3
assert lines[0] == 'First line,'
assert lines[2] == 'And last line.'

File file2 = new File( basedir, 'src/file2' );

assert file2.isFile()
assert file2.readLines() == ['Line 1', 'Line 2', 'New line']

File file3 = new File( basedir, 'src/file3' );

assert file3.isFile()
assert file3.readLines() == ['Line 1']
