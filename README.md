# Simple word count example with Spark and Scala.

## Dependencies

Spark runs in local mode. Dependencies are solely defined in build.sbt file. Note that JDK 10 isn't compatible with
Spark/Hadoop library (as of May 12, 2018), so for now this needs to use JDK 1.8.

## Code

Scala code is mostly self-explainatory. It loads the classic mapreduce research paper and runs the word-count example
in that paper's appendix section. Functional style avoids a lot of boilerplate codes, such as setting up mapper and
reducer classes.