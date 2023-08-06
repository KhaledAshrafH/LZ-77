# LZ-77 (Lempel-Ziv 77) Data Compression Algorithm

This repository contains a Java implementation of the LZ-77 data compression algorithm. LZ-77 is a lossless data compression method that replaces repetitive sequences of characters with references to previously occurring data. It achieves compression by storing the position and length of a match in a sliding window of previously processed data.

## Introduction

The Lempel-Ziv 77 (LZ-77) algorithm is a dictionary-based compression algorithm developed by Abraham Lempel and Jacob Ziv in 1977. It is widely used and forms the basis for many modern compression techniques.

The LZ-77 algorithm works by encoding repetitive sequences of characters as references to previously occurring data. It maintains a sliding window of previously processed data and uses this window to find matches for the current input. The algorithm replaces each repeated sequence with a pair of numbers: the distance (back-reference) to the start of the repeated sequence and the length of the sequence. This pair of numbers is known as a "tag."

## Usage

The LZ-77 compression algorithm is implemented in Java and consists of two main functions: `LZ77Compress` and `LZ77Decompress`. The `LZ77Compress` function compresses an input string using the LZ-77 algorithm, while the `LZ77Decompress` function decompresses a list of tags to retrieve the original text.

### Compression

The `LZ77Compress` function compresses a given input string using the LZ-77 algorithm. It takes a string as input and writes the compressed data to a file named `fileCompressed.txt`. The function also displays the tags, parts, and the original and compressed sizes of the data.

```java
LZ77Compress(inputString);
```

### Decompression

The LZ77Decompress function decompresses a list of tags using the LZ-77 algorithm. It takes an ArrayList<String> of tags as input and writes the decompressed text to a file named file.txt. The function also displays the tags and the decompressed text.

```java
LZ77Decompress(tags);
```

## Code Usage Illustration

Here's an illustration of how to use the LZ-77 compression algorithm in your Java code:
```
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        // Compress a string
        String inputString = "ABAABABAABBBBBBBBBBBBABBBBBBBBABABABABABABAABABABABBABBABABABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
        LZ77Compress(inputString);

        // Decompress using tags
        ArrayList<String> tags = new ArrayList<>();
        tags.add("<0,0,A>");
        tags.add("<0,0,B>");
        tags.add("<2,1,A>");
        tags.add("<3,2,B>");
        tags.add("<5,3,B>");
        tags.add("<2,2,B>");
        tags.add("<5,5,B>");
        tags.add("<1,1,A>");
        LZ77Decompress(tags);
    }
}
```

## License

The code in this repository is licensed under the MIT License.
