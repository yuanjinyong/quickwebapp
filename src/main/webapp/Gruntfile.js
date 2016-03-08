module.exports = function(grunt) {

    grunt.initConfig({
        jshint : {
            all : [ '../../../src/main/resources/static/js/**/*.js', '../../../src/test/resources/static/js/**/*.js' ],
            options : {
                globals : {
                    _ : false,
                    $ : false,
                    jasmine : false,
                    describe : false,
                    it : false,
                    expect : false,
                    beforeEach : false,
                    afterEach : false,
                    sinon : false
                },
                browser : true,
                devel : true
            }
        },
        testem : {
            unit : {
                options : {
                    framework : 'jasmine2',
                    launch_in_dev : [ 'PhantomJS' ],
                    before_tests : 'grunt jshint',
                    serve_files : [ '../../../src/main/webapp/node_modules/lodash/index.js',
                            '../../../src/main/webapp/node_modules/jquery/dist/jquery.js',
                            '../../../src/main/webapp/node_modules/sinon/pkg/sinon.js',
                            '../../../src/main/resources/static/js/**/*.js',
                            '../../../src/test/resources/static/js/**/*.js' ],
                    watch_files : [ '../../../src/main/resources/static/js/**/*.js',
                            '../../../src/test/resources/static/js/**/*.js' ]
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-testem');

    grunt.registerTask('default', ['testem:run:unit']);
};