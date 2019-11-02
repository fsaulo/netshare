# Netshare

Netshare is a concept of open source social media app with focus on privacy.

We are on a mission of making the internet a safer place for everyone. Our goal is to provide a way for humans to share their moments with friends without the worry of some evil corporation sneaking into their data and using it for malicious purposes.

## Building

#### Hacking on Netshare Core

This is an advanced guide into runing Netshare in Dev Mode with access to a local copy of Netshare source.

If you don't know what that means, you probably don't wanna follow it further.

#### Requirements

- Linux kernel with 64-bit or 32-bit architecture

- Java JDK 8 or latter

- Git

- JavaFX SDK library

- JDBC Driver

- MySQL local hosted server

#### Fork the fsaulo/netshare repository

Follow the [GitHub Help instructions on how to fork a repo](https://help.github.com/articles/fork-a-repo/).

##### Once you've set up your fork of the fsaulo/netshare repository, you can clone it to your local machine:

```command-line
$ git clone git@github.com:<your-username>/netshare.git
```

##### Now that everything is set up, certify that you have Java compiler into your system path:

```command-line
$ javac -version
```

If it returns a number, you're good to go.

![Javac Screenshot](https://raw.githubusercontent.com/fsaulo/netshare/master/external/readme/Screenshot%20from%202019-03-24%2014-10-28.png)

##### After that, you need to have a copy of both JDBC and JavaFX SDK libraries, the latter can be reached at [OpenFX](https://gluonhq.com/products/javafx/).

Having downloaded the appropriate JavaFX runtime for your operating system and unziped to desired location, proceed adding an environment variable pointing to the lib directory of the runtime:

#### Compiling and running

##### Linux

```command-line
$ export PATH_TO_FX=path/to/javafx-sdk-11.0.2/lib
```

You will also need to do the same for the [JDBC Driver](https://dev.mysql.com/downloads/connector/j/). Download the .jar file and put it in a desired location:

```command-line
$ export MYSQL=path/to/mysql-driver-connector.jar
```

Navigate to Netshare source code directory

```command-line
cd <em>path-to-your-local-netshare-repo</em>
```

Compile and run:

```command-line
$ javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml @build/source/src -d build/bin
```

```command-line
$ java -cp .:$MYSQL:build/bin --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml com.sys.Main
```

##### Windows

```command-line
C\:> set PATH_TO_FX="path\to\javafx-sdk-11.0.2\lib"
```

Download and install MySQL Pack from [here](https://dev.mysql.com/downloads/windows/installer/8.0.html).

Compile and run:

```command-line
C\:> javac --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml @build/source/src -d build/bin

```

```command-line
C\:> TODO

```

![Login Screenshot](https://raw.githubusercontent.com/fsaulo/netshare/master/resources/images/netshare_main_screen.png)

## Discussion

- Discuss Netshare on our [Telegram channel](https://t.me/netshare_channel)

## License

[GPL v3.0](https://github.com/fsaulo/netshare/blob/master/LICENSE)
