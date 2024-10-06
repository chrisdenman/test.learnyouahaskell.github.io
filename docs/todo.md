- If a dedicated download directory for repositories is supplied, we should use it to stop re-downloading
- Determine if we can just refresh ktor when a new repository version is downloaded?
- Add a runtime check to ensure interpolated file names contain unresolved tokens
- Make the (root) scripts work from any directory
- GitHub
    - [CONTRIBUTING.md](https://github.com/github/docs/blob/main/.github/CONTRIBUTING.md)
    - set branch protection rules to "Require review from Code Owners"
    - add an issue template
    - add a pull request template
- Is there value in using https for Ktor / Selenium?
- How do we support non-linux platforms, Vagrant?
- Do we need to consider Selenium Grid?
- Better method for spawning/killing Ktor
- cloning from SHAs
    - Can we tell the difference between an SHA and a branch name?
- Create a discussion on learnyouahaskell/learnyouahaskell.github.io re: setting image alt test attribute values
  correctly
- How do we manage browser versions?
  ```val capabilities = MutableCapabilities()
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "MicrosoftEdge")
    if (browserTarget.version != null) {
    capabilities.setCapability(CapabilityType.BROWSER_VERSION.toString(), browserTarget.version)
  ```