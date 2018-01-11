package usage;

import org.antlr.v4.runtime.InputMismatchException;
import parser.ClassesParser;

public class OperationEvaluation extends ClassEvaluation {
    private ClassesParser.OperationContext context;
    private String operation;

    /**
     * 操作文を設定します。
     *
     * @param text 設定する操作文
     */
    @Override
    public void setText(String text) {
        operation = text;
    }

    /**
     * <p> 操作文を取得します。 </p>
     *
     * <p>
     *     {@link #setText(String)}を実行する前にこのメソッドを実行した場合は{@code null}を返します。
     * </p>
     *
     * @return 操作文 {@code null}の可能性あり
     */
    @Override
    public String getText() {
        return operation;
    }



    /**
     * <p> 字句解析と構文解析を行い、構文解析木を走査します。 </p>
     *
     * <p>
     *     操作文を設定していない場合は{@link IllegalArgumentException}を投げます。
     * </p>
     */
    @Override
    public void walk() {
        initIfIsSameBetweenNameAndKeyword();
        if (operation == null) throw new IllegalArgumentException();

        ClassesParser parser = generateParser(operation);
        ClassesEvalListener listener = walk(parser.operation());
        context = listener.getOperation();

        try {
            extractName();
        } catch (InputMismatchException e) {
            setInputMismatchException(e);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR! Please Set Operation!");
        }
    }



    /**
     * <p> 操作名を抽出します。 </p>
     *
     * <p>
     *     次の場合は例外を投げます。
     *     <ul>
     *         <li>操作文を設定していない場合（{@link #setText(String)}参照） : {@link IllegalArgumentException}</li>
     *         <li>設定した操作文が予約語と同じ文字列の場合 : {@link ClassesParser.OperationContext#exception}</li>
     *     </ul>
     *
     *     また、処理の最後に{@code null}判定を行っているため（真の場合は上記の1番目の操作を行う）、戻り値が{@code null}の可能性は恐らくありません。
     * </p>
     *
     * @return 操作名
     */
    public String extractName() {
        String name = null;

        for (int i = 0; i < context.getChildCount(); i++) {
            if (context.getChild(i) instanceof ClassesParser.NameContext) {
                name = context.getChild(i).getText();
                break;
            }
            if (context.exception != null) throw context.exception;
        }
        if (name == null) throw new IllegalArgumentException();

        return name;
    }

    /**
     * <p> 可視性を抽出します。 </p>
     *
     * <p>
     *     次の場合は{@code null}を返します。
     *     <ul>
     *         <li> 操作文を設定していない場合 </li>
     *         <li> 設定した操作文に操作名を含んでいない場合 </li>
     *     </ul>
     * </p>
     *
     * @return 可視性 {@code null}の可能性あり
     */
    public String extractVisibility() {
        String visibility = null;

        for (int i = 0; i < context.getChildCount(); i++) {
            if (context.getChild(i) instanceof ClassesParser.VisibilityContext) {
                visibility = context.getChild(i).getText();
                break;
            }
        }

        return visibility;
    }

    /**
     * <p> 型を抽出します。 </p>
     *
     * <p>
     *     次の場合は{@code null}を返します。
     *     <ul>
     *         <li> 操作文を設定していない場合 </li>
     *         <li> 設定した操作文に操作名を含んでいない場合 </li>
     *     </ul>
     *
     *     また、設定した操作名が予約語と同じ文字列の場合は{@link org.antlr.v4.runtime.InputMismatchException}を投げます（{@link #extractName()}参照）。
     * </p>
     *
     * @return 型 {@code null}の可能性あり
     */
    public String extractReturnType() {
        String returnType = null;

        for (int i = 0; i < context.getChildCount(); i++) {
            if (context.getChild(i) instanceof ClassesParser.ReturnTypeContext) {
                returnType = context.getChild(i).getChild(0).getChild(1).getText();
                break;
            }
        }
        checkIfNameIsSamePrimitiveType();

        return returnType;
    }
}
