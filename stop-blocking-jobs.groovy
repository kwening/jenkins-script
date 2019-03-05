runningBuilds = Jenkins.instance.getAllItems(Job.class).collect { job->
  job.builds?.findAll { it.getResult().equals(null) && it.getActions().last() instanceof org.jenkinsci.plugins.workflow.support.steps.input.InputAction }.each { build ->
    println "---"
    
    // if
    Jenkins.instance.computers.each { computer ->
      computer.getExecutors().findAll{ build.externalizableId == it.getCurrentWorkUnit()?.work?.runId }.each { exec ->
        println "exec ${exec.getCurrentExecutable()?.getParent()} ${exec.getCurrentWorkUnit()?.work?.runId}"
        println "Killing ${build.externalizableId}"
      }
    }
  }
}.flatten()
