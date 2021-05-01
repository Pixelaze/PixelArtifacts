PixelArtifacts
==========

Основная информация
---
Текущая версия проекта: 0.1-SNAPSHOT
Версия Minecraft: 1.13-1.15
Ядро: Spigot
Зависимости: нет 

Описание
---
Плагин Minecraft, добавляющий археологию. Когда игрок копает в шахте камень, гранит, землю(и другие настраиваемые в конфиге блоки), он с некоторым шансом(настраиваевым в конфиге) получает один из предметов, созданных в конфиге.

Планы на будущее
---
1. Оптимизация плагина в связи с наличием не очень хорошего кода в некоторых частях плагина
2. Улучшение создания предметов, т. к. в данный момент он создает лишь базовые предметы с кастомным названием
3. Добавление возможности выдачи денег, опыта и т. п. в качестве артефактов
4. Возможность продажи артефактов скупщикам по цене, зависящей от редкости предмета

Конфигурационный файл
---
```yml
### CHANCES ###

DropChance: 100 #Percent of giving item while break. Default: 10
BadItemsBonus: 20 #Additional percent bonus by using
                  #bad items. If equals 20, the chance will
                  #be DropChance(default 10) + 20 = 30%
                  #Default: 20
SilkTouchModifier: 1.5 #Modifier of chance by using tool with Silk Touch

### ITEMS LIST ###

#List of all tools, using which you can get an artifact
#ADD ALSO BAD TOOLS!
#Default: all pickaxes
ToolsList:
    - "DIAMOND_PICKAXE"
    - "IRON_PICKAXE"
    - "STONE_PICKAXE"
    - "WOOD_PICKAXE"
    - "GOLD_PICKAXE"

#List of all bad tools, using which you have a bonus of
#BadItemsBonus. Default: WOODEN_PICKAXE and STONE_PICKAXE
BadToolsList:
    - "STONE_PICKAXE"
    - "WOOD_PICKAXE"

#List of all artifacts, that have chance to be founded
#while mining. Using: MATERIAL_NAME MIN MAX NAME RARITY
#MATERIAL_NAME - STONE, GRASS and etc.
#MIN - minimal amount to give.
#MAX - maximum amount to give
#NAME - name in inventory. REPLACE SPACES WITH _
#RARITY - number from 1 to 5 that presents rarity(stars) of artifact
ArtifactsList:
    - "STONE 1 4 &6Cool_Stone 1"
    - "DIAMOND_SWORD 1 1 &2&lAncient_Sword 5"
    - "PAPER 1 1 Maya_Ancient_Letter 5"

#List of materials, mining which activate a chance to get an
#artifact.
MaterialsList:
    - "STONE"
    - "IRON_ORE"

### MESSAGES AND LOCALE ###

#Displayed to player, when he founded an artifact
FoundedArtifactMessage: "&6You have founded an artifact!"

#Displayed when using /pixelartifacts help
PixelArtifactsHelpMessageList:
  - "/pixelartifacts reload - reload plugin" #reload
  - "/pixelartifacts credits - credits of author" #credits
  - "/pixelartifacts info - showing an info about plugin" #info
  - "/pixelartifacts get - get an artifact" #get
  - "/pixelartifacts list - list of artifacts" #list

#Dont right usage or errors in commands
PixelArtifactsGetUsage: "Usage: /pixelartifacts get [NUMBER IN LIST]"
PixelArtifactsGetTooBig: "There is no artifact with this number in list! Check /pixelartifacts list"

#Info message
PixelArtifactsInfoMessageList:
  - "Plugin Information"
  - "Chance: %CHANCE%" #%CHANCE% will be replaced
  - "Bad Tools Bonus: %BADTOOLSBONUS%" #%BADTOOLSBONUS% will be replaced
  - "Silk Touch Modifier: %SILKTOUCHMODIFIER%" #%SILKTOUCHMODIFIER% will be replaced
  - "Materials Count: %MATERIALSCOUNT%" #%MATERIALSCOUNT% will be replaced
  - "Artifacts Count: %ARTIFACTSCOUNT%" #%ARTIFACTSCOUNT% will be replaced
  - "Bad Tools Count: %BADTOOLSCOUNT%" #%BADTOOLSCOUNT% will be replaced
  - "Tools Count: %TOOLSCOUNT%" #%TOOLSCOUNT% will be replaced
  - "DEBUG MODE: %DEBUGMODE%" #%DEBUGMODE% will be replaced

### DEBUG INFORMATION ###
DebugMode: false #If enabled, an additional information will be
                 #given in logs. Use it only if you have some issues.
                 #Default: false

### PERMISSIONS ###
#There is only for helping you. DONT TOUCH THIS.

#Commands permissions:

#/pixelartifacts reload - pixelartifacts.command.reload
#/pixelartifacts list - pixelartifacts.command.list
#/pixelartifacts get - pixelartifacts.command.get
#/pixelartifacts info - pixelartifacts.command.info

#Artifacts permissions:

#Have chance to mine all artifacts - pixelartifacts.artifacts.mineartifact
#Have bad tools bonus - pixelartifacts.artifacts.badtoolsbonus
#Have silk touch multiplier - pixelartifacts.artifacts.silktouchmodifier
```
