package org.pf4j.demo.api;

import org.pf4j.ExtensionPoint;

/**
 * @author LSD
 * @date 2020/12/15 15:55
 */
public interface ProsthesisReceptacle<T> extends ExtensionPoint {
    /**
     * 拆解 配型 异常处理 输出
     */
    void dismantle(T t);
    void match(T t);
    void exception(T t);
    void sending(T t);
}
