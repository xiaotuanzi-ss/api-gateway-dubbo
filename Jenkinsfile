#!groovy

@Library('MicroserviceBuilder') _
microserviceBuilderPipeline {
  image = 'project-example-nacos-ewell'
  //tag = ''
  namespace = 'ewelldev'
  build = 'true'
  buildMethod = 'maven'           // maven(default)/npm
  deploy = 'true'
  qualityCheck = 'true'
  imageType = '4'
  imageShowType = '9'
  imageDescribe = '初始化jenkinsfile'
  imagePic = 'icon.png'
  deploymentNameCh = 'xxxx服务'
  imageStartupType = '0'
}
