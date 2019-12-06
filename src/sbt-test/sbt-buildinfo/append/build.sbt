lazy val check = taskKey[Unit]("check")

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin, ScriptedPlugin).
  settings(
    name := "helloworld",
    organization := "com.eed3si9n",
    version := "0.1",
    scalaVersion := "2.12.7",
    buildInfoKeys ++= Seq[BuildInfoKey](name, organization, version, scalaVersion,
      libraryDependencies, libraryDependencies in Test),
    buildInfoKeys += BuildInfoKey(resolvers),
    buildInfoPackage := "hello",
    homepage := Some(url("http://example.com")),
    licenses := Seq("MIT License" -> url("https://github.com/sbt/sbt-buildinfo/blob/master/LICENSE")),
    resolvers ++= Seq("Sonatype Public" at "https://oss.sonatype.org/content/groups/public"),
    check := {
      val f = (sourceManaged in Compile).value / "sbt-buildinfo" / ("%s.scala" format "BuildInfo")
      val lines = scala.io.Source.fromFile(f).getLines.toList
      lines match {
        case """// $COVERAGE-OFF$""" :: 
             """package hello""" ::
             """""" ::
             """import scala.Predef._""" ::
             """""" ::
             """/** This object was generated by sbt-buildinfo. */""" ::
             """case object BuildInfo {""" ::
             """  /** The value is "helloworld". */""" ::
             """  val name: String = "helloworld"""" ::
             """  /** The value is "0.1". */""" ::
             """  val version: String = "0.1"""" ::
             """  /** The value is "2.12.7". */""" ::
             """  val scalaVersion: String = "2.12.7"""" ::
             """  /** The value is "1.2.8". */""" ::
             """  val sbtVersion: String = "1.2.8"""" ::
             """  /** The value is "com.eed3si9n". */""" ::
             """  val organization: String = "com.eed3si9n"""" ::
             """  /** The value is scala.collection.immutable.Seq("org.scala-lang:scala-library:2.12.7", "org.scala-sbt:scripted-sbt:1.2.8:scripted-sbt", "org.scala-sbt:sbt-launch:1.2.8:scripted-sbt-launch"). */""" ::
             """  val libraryDependencies: scala.collection.immutable.Seq[String] = scala.collection.immutable.Seq("org.scala-lang:scala-library:2.12.7", "org.scala-sbt:scripted-sbt:1.2.8:scripted-sbt", "org.scala-sbt:sbt-launch:1.2.8:scripted-sbt-launch")""" ::
             """  /** The value is scala.collection.immutable.Seq("org.scala-lang:scala-library:2.12.7", "org.scala-sbt:scripted-sbt:1.2.8:scripted-sbt", "org.scala-sbt:sbt-launch:1.2.8:scripted-sbt-launch"). */""" ::
             """  val test_libraryDependencies: scala.collection.immutable.Seq[String] = scala.collection.immutable.Seq("org.scala-lang:scala-library:2.12.7", "org.scala-sbt:scripted-sbt:1.2.8:scripted-sbt", "org.scala-sbt:sbt-launch:1.2.8:scripted-sbt-launch")""" ::
             """  /** The value is scala.collection.immutable.Seq("Sonatype Public: https://oss.sonatype.org/content/groups/public"). */""" ::
             """  val resolvers: scala.collection.immutable.Seq[String] = scala.collection.immutable.Seq("Sonatype Public: https://oss.sonatype.org/content/groups/public")""" ::
             """  override val toString: String = {""" ::
             """    "name: %s, version: %s, scalaVersion: %s, sbtVersion: %s, organization: %s, libraryDependencies: %s, test_libraryDependencies: %s, resolvers: %s".format(""" ::
             """      name, version, scalaVersion, sbtVersion, organization, libraryDependencies, test_libraryDependencies, resolvers""" ::
             """    )""" ::
             """  }""" ::
             """}""" :: 
             """// $COVERAGE-ON$""" :: Nil =>
        case _ => sys.error("unexpected output: \n" + lines.mkString("\n"))
      }
      ()
    }
  )

