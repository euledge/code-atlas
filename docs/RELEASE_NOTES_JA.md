# リリースノート

## バージョン 1.1.0

**リリース日**: 未定

**GitHub**: [Pull Request #8](https://github.com/euledge/code-atlas/issues/8)

### ✨ 新機能

#### パッケージ階層の可視化改善

深いパッケージ階層を持つプロジェクト向けに、図の可読性を向上させる新しい設定オプションを追加しました。

- **`stripPackagePrefix`**: 図内のクラス名から共通のパッケージプレフィックスを自動的に削除します。
- **`groupByPackage`**: 植物UML（PlantUML）の `package` 構文や Mermaid の `namespace` 構文を使用して、クラスを視覚的にグループ化します。

**使用例:**

```kotlin
codeAtlas {
    stripPackagePrefix.set("com.example.")
    groupByPackage.set(true)
}
```

### 📝 関連イシュー

- Closes [#8](https://github.com/euledge/code-atlas/issues/8): Feature - Package Hierarchy Display Improvement

## バージョン 1.0.0

**リリース日**: 未定 (PR #4 マージ後)

**GitHub**: [Pull Request #4](https://github.com/euledge/code-atlas/pull/4)

### ⚠️ 破壊的変更

#### API変更: `rootPackage` → `rootPackages`

プラグインが複数のルートパッケージをサポートし、パッケージ間の依存関係分析が可能になりました。

**マイグレーションが必要:**

```kotlin
// ❌ 旧バージョン (v0.9.0)
codeAtlas {
    rootPackage.set("com.example")
}

// ✅ 新バージョン (v1.0.0)
codeAtlas {
    rootPackages.set(listOf("com.example"))
}
```

### ✨ 新機能

#### 複数ルートパッケージのサポート

異なるアーキテクチャレイヤー間の依存関係を分析するために、複数のパッケージプレフィックスを指定できます。

**ユースケース: DDDアーキテクチャ**
```kotlin
codeAtlas {
    rootPackages.set(listOf(
        "com.example.domain",
        "com.example.infrastructure",
        "com.example.application"
    ))
}
```

**コマンドライン:**
```sh
./gradlew generateDiagrams \
    --project-prop rootPackages=com.example.domain,com.example.infrastructure
```

**メリット:**
- 🏗️ レイヤー間の依存関係を可視化 (domain ↔ infrastructure)
- 📦 複数モジュール間の依存関係を分析
- 🎯 含めるパッケージをきめ細かく制御
- 📊 不要なパッケージを除外してより焦点を絞った図を作成

### 🔧 変更内容

#### コア実装

1. **CodeAtlasExtension.java**
   - 変更: `Property<String> getRootPackage()` → `ListProperty<String> getRootPackages()`
   - デフォルト: 空リスト (全パッケージをスキャン)

2. **CodeAtlasPlugin.java**
   - 追加: カンマ区切りパッケージリストのサポート
   - String と List 両方の入力タイプに対応

3. **CodeAtlasTask.java**
   - 更新: `List<String>` を ClassAnalyzer に渡す
   - 追加: `List` インポート

4. **ClassAnalyzer.java**
   - 更新: `List<String> rootPackages` パラメータを受け入れ
   - 使用: 配列変換で `acceptPackages(String...)` を使用

#### ドキュメント

5. **README.md**
   - 更新: すべての例を `rootPackages` に変更
   - 追加: DDDアーキテクチャのユースケース例
   - 更新: コマンドラインパラメータのドキュメント

6. **README_JA.md**
   - 更新: 日本語版も同様の変更

### 📦 バージョン情報

- **前バージョン**: 0.9.0
- **現バージョン**: 1.0.0
- **バージョンアップ理由**: 破壊的API変更

### 🚀 アップグレードガイド

#### ステップ1: プラグインバージョンの更新

```kotlin
plugins {
    id("io.github.euledge.code-atlas") version "1.0.0"
}
```

#### ステップ2: 設定の更新

```kotlin
codeAtlas {
    // 変更前:
    // rootPackage.set("com.example")
    
    // 変更後:
    rootPackages.set(listOf("com.example"))
}
```

#### ステップ3: テスト

```sh
./gradlew generateDiagrams
```

### 📝 関連イシュー

- Closes [#3](https://github.com/euledge/code-atlas/issues/3): 機能リクエスト - 複数ルートパッケージのサポート

### 🙏 謝辞

このリリースにフィードバックを提供してくださったすべての貢献者とユーザーの皆様に感謝いたします!

---

## バージョン 0.9.0

初回公開リリース。基本機能を提供:
- PlantUML図の生成
- Mermaid図の生成
- 単一ルートパッケージフィルタリング
- コマンドライン設定サポート
