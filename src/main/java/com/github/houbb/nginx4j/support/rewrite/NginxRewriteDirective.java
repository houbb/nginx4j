package com.github.houbb.nginx4j.support.rewrite;

public interface NginxRewriteDirective {

    NginxRewriteDirectiveResult handleRewrite(NginxRewriteDirectiveContext context);

}
