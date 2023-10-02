rootProject.name = "go-game"

include("errors")
project(":errors").projectDir = file("modules/errors")

include("game")
project(":game").projectDir = file("modules/game")



