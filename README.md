# Automated fixes task

## Task 2

### Input

The program accepts from the stdin five absolute paths separated by whitespaces:

First Bugban analysis output file
Second Bugban analysis output file
Program output file for the problems only detected in the first analysis
Program output file for the problems only detected in the second analysis
Program output file for the problems detected in both analyses

### Output

Write to the three output files (paths specified as an input) problems detected only in the first Bugban analysis, only in the second, and detected in both. The files' content is JSON formatted according to the schema above.

### Testing:

Please use `gradlew test` to run tests

