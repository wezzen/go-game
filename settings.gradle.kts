rootProject.name = "go-game"

include("base")
project(":base").projectDir = file("modules/base")

include("base-bot")
project(":base-bot").projectDir = file("modules/bots/base-bot")

include("errors")
project(":errors").projectDir = file("modules/errors")

include("game")
project(":game").projectDir = file("modules/game")

include("selfplay")
project(":selfplay").projectDir = file("modules/selfplay")

include("random-bot")
project(":random-bot").projectDir = file("modules/bots/random")

include("client")
project(":client").projectDir = file("modules/client")

include("server")
project(":server").projectDir = file("modules/server")

include("writers")
project(":writers").projectDir = file("modules/writers")

