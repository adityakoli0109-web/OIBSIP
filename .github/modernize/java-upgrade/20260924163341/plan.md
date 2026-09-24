# Upgrade Plan: train-reservation-system (20260924163341)

- **Generated**: 2026-09-24 16:34
- **HEAD Branch**: main
- **HEAD Commit ID**: determined by version control during execution

## Available Tools

**JDKs**
- JDK 17: not available (baseline will be skipped)
- JDK 25.0.3: C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot\bin (target runtime)

**Build Tools**
- Maven 3.9.16: C:\Users\Dell\Downloads\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260924163341
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade Java runtime/compiler target from 17 to 25 (latest LTS)

## Technology Stack

| Technology/Dependency | Current | Min Compatible Version | Why Incompatible |
| --------------------- | ------- | ---------------------- | ---------------- |
| Java | 17 | 25 | User-requested runtime upgrade |
| Maven | 3.9.16 | 3.9+ | Compatible with Java 25; no upgrade required |
| MySQL Connector/J | 9.4.0 | 9.4.0 | No Java-target incompatibility identified |
| Java Swing/JDBC | JDK APIs | 25 | APIs used remain available on Java 25 |
| exec-maven-plugin | 3.5.0 | 3.5.0 | No Java-target incompatibility identified |

## Derived Upgrades

- Update `maven.compiler.source` and `maven.compiler.target` to 25 so Maven emits Java 25 bytecode.
- No Kotlin upgrade is required because the project contains no Kotlin sources or Kotlin build configuration.
- No Maven upgrade is required because Maven 3.9.16 is compatible with the target JDK.
- No dependency replacement is required; the application uses standard Swing/JDBC APIs and MySQL Connector/J.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|------------|---------|--------|--------|--------|
| pom.xml | maven.compiler.source | 17 | upgrade | 25 | Set Java source level to the requested latest LTS |
| pom.xml | maven.compiler.target | 17 | upgrade | 25 | Set Java bytecode target to the requested latest LTS |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|-----------------|--------|
| None | N/A | No JDK-internal imports or removed APIs found | No source changes | Existing Swing, JDBC, SQL, and `java.time` APIs are supported on Java 25 |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|-----------------|--------|
| None | N/A | No runtime configuration requires a Java-version change | No change | The application configuration is database/UI-specific |

### CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|-----------------|
| None | N/A | No CI/CD files detected | No change |

### Documentation Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|-----------------|--------|
| README.md | Technology and setup | Java 17; JDK 17 or newer | Change references to Java 25 and JDK 25 or newer | Keep setup instructions aligned with the supported runtime |
| INTERNSHIP_PROJECT_REPORT.txt | Technology list | Java 17 | Change to Java 25 | Prevent documentation drift |
| run instructions.txt | Requirements | JDK 17+ | Change to JDK 25+ | Match the upgraded runtime requirement |

### Risks & Warnings

- **No baseline runtime**: Java 17 is not installed, so pre-upgrade compilation and test results cannot be captured. **Mitigation**: Record baseline as skipped and run clean compile/test validation on Java 25.
- **Database-dependent UI**: Existing tests are absent and `mvn test` may not exercise the Swing/MySQL workflow. **Mitigation**: Require clean test execution and document that runtime database behavior remains outside automated coverage.
- **Java 25 compiler support**: The installed Maven compiler configuration relies on Maven's default compiler plugin. **Mitigation**: Validate both main and test compilation with Maven 3.9.16 and JDK 25; add a compiler plugin only if the build exposes a compatibility issue.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Confirm the target JDK and Maven installation before editing the project.
  - **Changes to Make**: Use the installed JDK 25.0.3 and Maven 3.9.16.
  - **Verification**: Confirm tool paths; expected both are available.

- Step 2: Setup Baseline
  - **Rationale**: Capture the pre-upgrade result when the current JDK is available.
  - **Changes to Make**: None; Java 17 is unavailable, so this step is skipped.
  - **Verification**: Skipped with documented reason.

- Step 3: Upgrade Java Target and Documentation
  - **Rationale**: Apply the requested Java 25 compiler target and align all project instructions.
  - **Changes to Make**: Apply Dependency Changes and Documentation Changes from Impact Analysis.
  - **Verification**: Run `mvn clean test-compile -q` using JDK 25; expected successful main and test compilation.

- Step 4: Final Validation
  - **Rationale**: Confirm the clean Java 25 build and full test suite after the upgrade.
  - **Changes to Make**: Resolve any compilation or test failures caused by the upgrade.
  - **Verification**: Run `mvn clean test -q` using JDK 25; expected 100% pass rate. Also run `mvn clean verify -Djacoco.skip=false` where supported.

- Step 5: CVE Validation and Fix
  - **Rationale**: Check the explicitly versioned direct dependency after the build change.
  - **Changes to Make**: Scan `com.mysql:mysql-connector-j:9.4.0`; upgrade only if a vulnerability is reported.
  - **Verification**: Re-scan after any fix and run clean test compilation.

- Step 6: Summary and Cleanup
  - **Rationale**: Record results, residual risks, and final validation evidence.
  - **Changes to Make**: Generate the upgrade summary and remove temporary metadata where appropriate.
  - **Verification**: Confirm all plan steps are completed and success criteria are recorded.
