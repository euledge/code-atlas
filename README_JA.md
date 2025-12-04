# Code Atlas Gradle プラグイン
README [英語版](README.md) [日本語版](README_JA.md)
 
## 概要

**Code Atlas** Gradle プラグインは、プロジェクトのコンパイル済み Java クラスを解析し、次の 3 つの形式でクラス図を生成します。
- **PlantUML**（`.puml`）
- **Mermaid**（`.mmd`）
- **Draw.io**（`.drawio`）

アーキテクチャや依存関係、継承・インタフェース実装を可視化したいときに便利です。

## 使い方

1. **プラグインを適用**（`build.gradle.kts` または `build.gradle`）:
   ```kotlin
   plugins {
       id("com.example.codeatlas") version "1.0.0"
   }
   ```
2. **拡張設定**（任意）:
   ```kotlin
   codeAtlas {
       formats.set(listOf("plantuml", "mermaid", "drawio"))
       outputDir.set("docs/diagrams")
   }
   ```
   - `formats` – 生成したい図のフォーマット一覧。
   - `outputDir` – 図ファイルを書き出すディレクトリ。
3. **タスクを実行**:
   ```sh
   ./gradlew generateDiagrams
   ```
   タスクはプロジェクトをコンパイル（必要なら）し、クラスをスキャンして設定した出力ディレクトリに図を作成します。

## サンプルプロジェクト

`sample-project` ディレクトリに最小構成のサンプルがあります。プラグインをローカル Maven リポジトリに公開した後（`./gradlew publishToMavenLocal`）、以下を実行してください。
```sh
cd sample-project
../gradlew generateDiagrams
```
生成された図は `sample-project/docs/diagrams` に配置されます。

## 前提条件

- Java 21（または互換性のある JDK）
- Gradle 8.5 以上
- クラスパススキャンに **ClassGraph** を使用しています。

## ライセンス

[MIT License](LICENSE.md)
