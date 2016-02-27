/**
 * 
 */
package com.quickwebapp.tools.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.generator.api.ProgressCallback;

/**
 * @author Administrator
 *
 */
public class MyBatisGeneratorProgressCallback implements ProgressCallback {
    private static Log logger = LogFactory.getLog(MyBatisGeneratorProgressCallback.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.ProgressCallback#introspectionStarted(int)
     */
    @Override
    public void introspectionStarted(int totalTasks) {
        logger.debug("Introspection started, totalTasks: " + totalTasks);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.ProgressCallback#generationStarted(int)
     */
    @Override
    public void generationStarted(int totalTasks) {
        logger.debug("Generation started, totalTasks: " + totalTasks);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.ProgressCallback#saveStarted(int)
     */
    @Override
    public void saveStarted(int totalTasks) {
        logger.debug("Save started totalTasks: " + totalTasks);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.ProgressCallback#startTask(java.lang.String)
     */
    @Override
    public void startTask(String taskName) {
        logger.debug("Start task, taskName: " + taskName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.ProgressCallback#done()
     */
    @Override
    public void done() {
        logger.debug("done");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mybatis.generator.api.ProgressCallback#checkCancel()
     */
    @Override
    public void checkCancel() throws InterruptedException {
        logger.debug("Check cancel");
    }

}
