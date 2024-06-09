package com.github.houbb.nginx4j.support.index;

import com.github.houbb.nginx4j.support.request.dispatch.NginxRequestDispatchContext;

import java.io.File;

public interface NginxIndexFile {

    File getIndexFile(final NginxRequestDispatchContext context);

}
