[![Build Status](https://travis-ci.org/paveyry/LyreLand.svg?branch=master)](https://travis-ci.org/paveyry/LyreLand)

LyreLand
========

Status
------

This project is not maintained anymore. Please use at your own risks.

Overview
--------

LyreLand is a music composition AI that uses deep artificial neural networks (LSTM) to generate music.
It also contains a package used for more traditional music analysis of MIDI files (harmonic, melodic, and rhythmic analysis) that can be used to generate music procedurally using Markov Chains trained using the analysis of a dataset.

Dependencies
------------

LyreLand is developed using JAVA 8. It uses the jMusic library to generate
MIDI scores and Fluidsynth to produce WAV files using a SF2 GM soundfont.
It also uses deeplearning4j for the LSTM implementation and Spark for the web interface and website.

File storage
------------

This repository uses git lfs to store binary files. You need to install git-lfs
to work on this repository.

Building
--------

The project dependencies are handles using maven. We use the plugin
`appassembler` to provide simple run scripts.
To build the project, run the following command:

```
mvn package appassembler:assemble
```

Running
-------

Once the project is built, you can run the script with the following command:

```
sh target/appassembler/bin/app
```

If you want to run the servlet, use the following command:

```
sh target/appasembler/bin/webserv
```
