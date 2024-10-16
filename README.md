# [test.learnyouahaskell.github.io 0.1.0](https://github.com/chrisdenman/test.learnyouahaskell.github.io)

This project provides functional testing for
the [Learn You A Haskell for Great Good](https://github.com/learnyouahaskell/learnyouahaskell.github.io) GitHub project,
which is an
open-source fork of the most excellent [Learn You a Haskell for Great Good](https://learnyouahaskell.com/) by Miran
Lipovača.

*** 

## Configuration

A typical configuration file for executing the tests is as follows:

<pre>
{
  "id": "production",                                 🔵 An identifier for this configuration. 
  "github": {                                         🟣 Git Hub configuration... 
    "owner": "learnyouahaskell",                      🔵 The owner of the repository to test. 
    "repository": "learnyouahaskell.github.io",       🔵 The repository to test. 
    "branch": "main",                                 🔵 The branch to test.
    "html_content_root": "docs",                      🔵 The relative repository path from which to serve content 
  },
  "tests": {                                    
    "relative_page_urls": [                           🟣 A list of relative URL strings to screen-shot...
      "",
      "faq.html",
      "chapters.html"
    ],
    "screenshots": {                                  🟣 Screenshot configuration... 
                                                      🔵 A string interpolation template screenshot filenames.
                                                      🔵   Path segments are separated with '/' which are replaced with their 
                                                      🔵   platform dependent equivalents at run-time.
                                                      🔵 See later on for a full list of permitted tokens.
      "output_file_template": "{{user.dir}}/screenshots/{{github.owner}}-{{github.repository}}-{{github.branch}}/{{github.sha}}/{{platform.name}}/{{browser.name}}-{{browser.version}}/{{browser.width}}x{{browser.height}}/{{relative_page_url}}_{{tile.index}}.{{screenshots.image_format}}",
                                                      🔵 A Java DateTimeFormatter pattern for formatting the 'start_time_utc'.  
      "dateTime_formatter_pattern": "uuuu-MM-dd'T'HH:mm:ss.SSSX", 
      "maximum_height_pixels": 2048,                  🔵 The maximum height of a screenshot in pixels. 
                                                      🔵   Taller images are split into numbered tiles.
      "scroll_timeout_milliseconds": 100,             🔵 The maximum time to wait for Selenium to scroll the page under test.
      "image_format": "png"                           🔵 The screenshot output image format.
    },                                                
    "browsers": {                                     
      "targets": [                                    🔵 An array of browser name specifications. 
                                                      🔵   Only: 'chrome', 'edge' & 'firefox' are currently permitted.
        {"name": "chrome"},                           
        {"name": "firefox"}                           
      ],                                              
      "dimensions": [                                 🟣 A JSON array of browser window dimensions...
        {                                             
          "width": 860,                               🔵 Desired browser width in pixels.
          "height": 800                               🔵 Desired browser height in pixels.
        }                                          
      ]                                            
    },                                             
    "remote-web-driver": {                            🟣 Selenium configuration...
      "scheme": "http",                               🔵 The URL scheme. Only 'http' currently permitted.
      "bind-address": "0.0.0.0",                      🔵 The IP address or host to bind to.
      "port": 4444                                    🔵 The port number on which to expose Selenium.
    }                                              
  },                                               
  "serving": {                                        🟣 HTTP server configuration... 
    "port": 8888,                                     🔵 HTTP port number. 
    "tls": {                                          🟣 Optional TLS configuration for HTTPS serving...
      "certificate-alias": "test.lyah"                🔵 Optional keystore certificate alias
      "keystore-filename": "build/lyah_keystore.jks", 🔵 Optional location for the generated JVM keystore location
      "port": 8443                                    🔵 HTTPs port number.                                    
    }
  }
}
</pre>

### Tokens and their substitutions in `output_file_template`

The following tokens and their corresponding replacements, are listed below:

| Token                                   | Substitution                                                                                    |
|-----------------------------------------|-------------------------------------------------------------------------------------------------|
| `{{id}}`                                | The configured `id` value.                                                                      |
| `{{github.owner}}`                      | The configured `github.owner` value.                                                            |
| `{{github.repository}}`                 | The configured `github.repository` value.                                                       |
| `{{github.branch}}`                     | The configured `github.branch` value.                                                           |
| `{{github.sha}}`                        | The generated `github.sha` value which is the branch under test's git SHA.                      |
| `{{github.html_content_root}}`          | The configured `github.html_content_root` value.                                                |
| `{{screenshots.maximum_height_pixels}}` | The maximum height of a screenshot in pixels. Taller images are automatically split into tiles. |
| `{{screenshots.image_format}}`          | The configured `screenshots.image_format` value.                                                |
| `{{relative_page_url}}`                 | The current test's configured, `relative_page_url` value.                                       |
| `{{browser.name}}`                      | The configured `browser.name` value.                                                            |
| `{{browser.version}}`                   | The current test's browser version as reported by Selenium.                                     |
| `{{browser.x}}`                         | The current test's browser x-coordinate position as reported by Selenium.                       |
| `{{browser.y}}`                         | The current test's browser y-coordinate position as reported by Selenium.                       |
| `{{browser.width}}`                     | The configured browser's width value.                                                           |
| `{{browser.height}}`                    | The configured browser's height value.                                                          |
| `{{start_time_utc}}`                    | The start time of the screenshot tests in UTC and formatted using `dateTime_formatter_pattern`  |
| `{{tile.index}}`                        | The current screenshot's tile index number (0-based). Always defined.                           |
| `{{platform.name}}`                     | The platform's name as reported by Selenium.                                                    |
| `{{platform.major_version}}`            | The platform's major version number as reported by Selenium.                                    |
| `{{platform.minor_version}}`            | The platform's minor version number as reported by Selenium.                                    |

**All Java System Properties and Java Environment Variables are also available.**

#### Operation

1. Token substitution is performed initially.
2. Then all forward-slash characters '/' are replaced by their platform-dependant equivalents.

***

## System Requirements

- Java (see [Testing](#Testing))

Although the JVM/Gradle applications may be run using any system with a suitable Java installation. The supporting scripts
are written in Bash and likely require an up-to-date Linux based system.

The Bash scripts check their own dependency requirements.

SDKMAN! initialization files [`.sdkmanrc`](./.sdkmanrc) files are included for convenience.

# Execution

## Tests

The Selenium tests (including the screenshotting) are executed by invoking the [`test`](./test) script in the
repository's root
directory passing the path to the configuration file as the sole argument's value.

More information can be found within the [`test`](./test) script itself, but as an example:

```shell
./test $(pwd)/config/learnyouahaskell/main.json
```

## Comparing Output Images

The intention of the screenshot functionality is twofold:

1. To provide quick comfort that [learnyouahaskell.github.io](https://learnyouahaskell.github.io) has not
   suffered any drastic regression.
2. To provide an easy means of comparing page presentation across different browsers spanning code and content changes.

The included script [`comp`](./comp) may be used to compare screenshot output sets. This is useful when
comparing screenshots from a known good branch with those of another.

The [`comp`](./comp) script compares two directories of images to firstly, ensure they contain identically named files,
and
subsequently compares the images using `ImageMagick` signature functionality to ensure they are indeed invariant.

When two identically named images are found to differ, `ImageMagick` is utilized once again to produce a flicker diagram
which helps greatly in highlighting differences between two presumed identical images.

A typical invocation of the [`comp`](./comp) script would look something like:

```shell
./comp \
  screenshots/branch1/firefox/1024x800 \      # 🔵 Images path 1
  screenshots/branch2/firefox/1024x800 \      # 🔵 Images path 2
  "png" \                                     # 🔵 Input image file extension (case insensitive)
  out/firefox/1024x800                        # 🟢 Output directory 
```

More information can be found within the [`comp`](./comp) script itself.

***

# Contributing

Please read the [guide](.github/CONTRIBUTING.md) to get involved.

***

# Links

- [Ktor - ktor.io](https://ktor.io/)
- [gnome-terminal - github.com](https://github.com/GNOME/gnome-terminal)
- [Gradle - gradle.org](https://gradle.org/)
- [ImageMagick - imagemagick.org](https://imagemagick.org/)
- [Java DateTimeFormatter - oracle.com](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/format/DateTimeFormatter.html)
- [Java System Properties - oracle.com](https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html)
- [SDKMAN! - sdkman.io](https://sdkman.io/)
- [Selenium - www.selenium.dev](https://www.selenium.dev/)
- [BASH Reference Manual - gnu.org](https://www.gnu.org/software/bash/manual/bash.html)

***

# Testing

| OS                 | Java           | Gradle(w) | Selenium Server | SDKMAN! (script) | SDKMAN! (native) | Kotlin | Bash              | ImageMagick                |
|--------------------|----------------|-----------|-----------------|------------------|------------------|--------|-------------------|----------------------------|
| Ubuntu 24.04.1 LTS | 21.0.2-graalce | 8.10      | 4.25.0          | 5.18.2           | 0.4.6            | 2.0.20 | 5.2.21(1)-release | 6.9.12-98 Q16 x86_64 18038 |

## Code of Conduct

Please read our [code of conduct](.github/CODE_OF_CONDUCT.md)

## Licensing

The [Unlicense](./LICENSE)
