package io.github.morichan.fescue.feature.value.expression.symbol;

/**
 * <p> ドットクラス </p>
 *
 * <p>
 *     演算子におけるドット{@code "."}クラスです。
 *     {@link io.github.morichan.fescue.feature.value.expression.symbol}パッケージおよびこのクラスを利用する{@link io.github.morichan.fescue.feature.value.expression.Expression}抽象クラスを実装したクラスにおいて、
 *     ドットとは限定名（または修飾名）と呼ばれるリテラル記法のことを指します（例、{@code String.method(), String.value}などにおけるドット{@code "."}）。
 * </p>
 *
 * <p>
 *     正確にはドットはリテラル記法であり、演算子ではない（諸説あり、Java SE 7 Editionではドットは演算子として定義している）が、このモジュールでは演算子として定義する。
 * </p>
 */
public class Dot extends Symbol {

    /**
     * <p> 演算子の両端にスペースを保持している場合は真を返す真偽値判定を行う。 </p>
     *
     * @return 偽
     */
    @Override
    public boolean isHadSpaceBothSides() {
        return false;
    }

    /**
     * <p> 加算演算子の文字列を取得します。 </p>
     *
     * @return {@code "."}
     */
    @Override
    public String toString() {
        return ".";
    }
}
