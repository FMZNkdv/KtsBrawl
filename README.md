## KtsBrawl ![K](https://img.shields.io/badge/Kotlin-5A17EB?style=flat&logo=kotlin&logoColor=white) ![J](https://img.shields.io/badge/OpenJdk-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![Screenshot](https://raw.githubusercontent.com/FMZNkdv/KtsBrawl/refs/heads/main/Screenshot.jpg?token=GHSAT0AAAAAADLBIMCLHPNVZBLO6IVYY5JM2IB6BFA)
This is the Brawl Stars Core, version 55.211.1, written in Kotlin. This server emulates libg.so with minor server logic.

## Requirements

- Java 21 or higher
- Kotlin compiler (included in build)
- Brain..? 🧑🏿‍🦯

## Building

```bash
git clone https://github.com/FMZNkdv/KtsBtawl.git
cd KtsBrawl

kotlinc -include-runtime -d ktsbrawl.jar `find src -name "*.kt"`
```

## Running

```bash
java -jar ktsbrawl.jar
```

The server will start listening on `0.0.0.0:9339` by default.
