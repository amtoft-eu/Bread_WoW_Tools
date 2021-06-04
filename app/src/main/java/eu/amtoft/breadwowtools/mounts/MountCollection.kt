package eu.amtoft.breadwowtools.mounts

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import eu.amtoft.breadwowtools.*
import eu.amtoft.breadwowtools.Expansion.*
import eu.amtoft.breadwowtools.Reset.*
import eu.amtoft.breadwowtools.api.MountContainer
import eu.amtoft.breadwowtools.characters.CharacterCollection

object MountCollection {
    var mounts: ArrayList<Mount> = ArrayList()
    var unknownMounts: ArrayList<Mount> = ArrayList()

    init {
        mounts.add(
            Mount(
                69,
                "Rivendare's Deathcharger",
                VANILLA,
                "Stratholme",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_undeadhorse
            )
        )
        mounts.add(
            Mount(
                117,
                "Blue Qiraji Battle Tank",
                VANILLA,
                "Temple of Ahn'Qiraj",
                0.0,
                WEEKLY,
                R.drawable.inv_misc_qirajicrystal_04
            )
        )
        mounts.add(
            Mount(
                118,
                "Red Qiraji Battle Tank",
                VANILLA,
                "Temple of Ahn'Qiraj",
                0.0,
                WEEKLY,
                R.drawable.inv_misc_qirajicrystal_02
            )
        )
        mounts.add(
            Mount(
                119,
                "Yellow Qiraji Battle Tank",
                VANILLA,
                "Temple of Ahn'Qiraj",
                0.0,
                WEEKLY,
                R.drawable.inv_misc_qirajicrystal_01
            )
        )
        mounts.add(
            Mount(
                120,
                " Green Qiraji Battle Tank",
                VANILLA,
                "Temple of Ahn'Qiraj",
                0.0,
                WEEKLY,
                R.drawable.inv_misc_qirajicrystal_03
            )
        )
        mounts.add(
            Mount(
                183,
                "Ashes of Al'ar",
                TBC,
                "Tempest Keep",
                2.0,
                WEEKLY,
                R.drawable.inv_misc_summerfest_brazierorange
            )
        )
        mounts.add(
            Mount(
                168,
                "Fiery Warhorse",
                TBC,
                "Karazhan",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_dreadsteed
            )
        )
        mounts.add(
            Mount(
                185,
                "Raven Lord",
                TBC,
                "Sethekk Halls",
                1.0,
                DAILY,
                R.drawable.inv_mount_raven_54
            )
        )
        mounts.add(
            Mount(
                213,
                "Swift White Hawkstrider",
                TBC,
                "Magister's Terrace",
                3.0,
                DAILY,
                R.drawable.ability_mount_cockatricemountelite_white
            )
        )
        mounts.add(
            Mount(
                237,
                "White Polar Bear",
                WOTLK,
                "The Storm Peaks",
                3.0,
                DAILY,
                R.drawable.ability_mount_polarbear_white
            )
        )
        mounts.add(
            Mount(
                246,
                "Azure Drake",
                WOTLK,
                "Eye of Eternity",
                5.0,
                WEEKLY,
                R.drawable.ability_mount_drake_azure
            )
        )
        mounts.add(
            Mount(
                247,
                "Blue Drake",
                WOTLK,
                "Eye of Eternity",
                5.0,
                WEEKLY,
                R.drawable.ability_mount_drake_azure
            )
        )
        mounts.add(
            Mount(
                248,
                "Bronze Drake",
                WOTLK,
                "The Culling of Stratholme",
                100.0,
                DAILY,
                R.drawable.ability_mount_drake_bronze
            )
        )
        mounts.add(
            Mount(
                250,
                "Twilight Drake",
                WOTLK,
                "The Obsidian Sanctum",
                100.0,
                WEEKLY,
                R.drawable.ability_mount_drake_twilight
            )
        )
        mounts.add(
            Mount(
                253,
                "Black Drake",
                WOTLK,
                "The Obsidian Sanctum",
                100.0,
                WEEKLY,
                R.drawable.ability_mount_drake_twilight
            )
        )
        mounts.add(
            Mount(
                264,
                "Blue Proto-Drake",
                WOTLK,
                "Utgarde Pinnacle",
                1.0,
                DAILY,
                R.drawable.ability_mount_drake_proto
            )
        )
        mounts.add(
            Mount(
                286,
                "Grand Black War Mammoth",
                WOTLK,
                "Vault of Archavon",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_mammoth_black_3seater
            )
        )
        mounts.add(
            Mount(
                287,
                "Grand Black War Mammoth",
                WOTLK,
                "Vault of Archavon",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_mammoth_black_3seater
            )
        )
        mounts.add(
            Mount(
                304,
                "Mimiron's Head",
                WOTLK,
                "Ulduar",
                1.0,
                WEEKLY,
                R.drawable.inv_misc_enggizmos_03
            )
        )
        mounts.add(
            Mount(
                349,
                "Onyxian Drake",
                WOTLK,
                "Onyxia's Lair",
                1.0,
                WEEKLY,
                R.drawable.achievement_boss_onyxia
            )
        )
        mounts.add(
            Mount(
                363,
                "Invincible",
                WOTLK,
                "Icecrown Citadel",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_pegasus
            )
        )
        mounts.add(
            Mount(
                395,
                "Drake of the North Wind",
                CATA,
                "Vortex Pinnacle",
                1.0,
                DAILY,
                R.drawable.inv_misc_stormdragonpale
            )
        )
        mounts.add(
            Mount(
                396,
                "Drake of the South Wind",
                CATA,
                "Throne of the Four Winds",
                1.0,
                WEEKLY,
                R.drawable.inv_misc_stormdragonpurple
            )
        )
        mounts.add(
            Mount(
                397,
                "Vitreous Stone Drake",
                CATA,
                "Stonecore",
                1.0,
                DAILY,
                R.drawable.inv_misc_stonedragonblue
            )
        )
        mounts.add(
            Mount(
                410,
                "Armored Razzashi Raptor",
                CATA,
                "Zul'Gurub",
                1.0,
                DAILY,
                R.drawable.ability_mount_raptor
            )
        )
        mounts.add(
            Mount(
                411,
                "Swift Zulian Panther",
                CATA,
                "Zul'Gurub",
                1.0,
                DAILY,
                R.drawable.ability_mount_blackpanther
            )
        )
        mounts.add(
            Mount(
                415,
                "Pureblood Fire Hawk",
                CATA,
                "Firelands",
                1.0,
                WEEKLY,
                R.drawable.inv_misc_orb_05
            )
        )
        mounts.add(
            Mount(
                419,
                "Amani Battle Bear",
                CATA,
                "Zul'Aman",
                100.0,
                DAILY,
                R.drawable.ability_druid_challangingroar
            )
        )
        mounts.add(
            Mount(
                425,
                "Flametalon of Alysrazor",
                CATA,
                "Firelands",
                2.0,
                WEEKLY,
                R.drawable.ability_mount_fireravengodmount
            )
        )
        mounts.add(
            Mount(
                442,
                "Blazing Drake",
                CATA,
                "Dragon Soul",
                3.0,
                WEEKLY,
                R.drawable.ability_mount_drake_red
            )
        )
        mounts.add(
            Mount(
                444,
                "Life-Binder's Handmaiden",
                CATA,
                "Dragon Soul",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_drake_red
            )
        )
        mounts.add(
            Mount(
                445,
                "Experiment 12-B",
                CATA,
                "Dragon Soul",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_drake_twilight
            )
        )
        mounts.add(
            Mount(
                473,
                "Heavenly Onyx Cloud Serpent",
                MOP,
                "Kun-Lai Summit",
                0.1,
                WEEKLY,
                R.drawable.inv_pandarenserpentgodmount_black
            )
        )
        mounts.add(
            Mount(
                478,
                "Astral Cloud Serpent",
                MOP,
                "Mogu'shan Vaults",
                1.0,
                WEEKLY,
                R.drawable.inv_celestialserpentmount
            )
        )
        mounts.add(
            Mount(
                515,
                "Son of Galleon",
                MOP,
                "Valley of the Four Winds",
                0.1,
                WEEKLY,
                R.drawable.inv_mushanbeastmount
            )
        )
        mounts.add(
            Mount(
                531,
                "Spawn of Horridon",
                MOP,
                "Throne of Thunder",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_triceratopsmount
            )
        )
        mounts.add(
            Mount(
                533,
                "Cobalt Primordial Direhorn",
                MOP,
                "Isle of Giants",
                0.1,
                WEEKLY,
                R.drawable.ability_mount_triceratopsmount_blue
            )
        )
        mounts.add(
            Mount(
                542,
                "Thundering Cobalt Cloud Serpent",
                MOP,
                "Isle of Thunder",
                0.1,
                WEEKLY,
                R.drawable.inv_pandarenserpentmount_lightning_blue
            )
        )
        mounts.add(
            Mount(
                543,
                "Clutch of Ji-Kun",
                MOP,
                "Throne of Thunder",
                1.0,
                WEEKLY,
                R.drawable.achievement_boss_ji_kun
            )
        )
        mounts.add(
            Mount(
                559,
                "Kor'kron Juggernaut",
                MOP,
                "Siege of Orgrimmar",
                1.0,
                WEEKLY,
                R.drawable.inv_mount_hordescorpiong
            )
        )
        mounts.add(
            Mount(
                613,
                "Ironhoof Destroyer",
                WOD,
                "Blackrock Foundry",
                1.0,
                WEEKLY,
                R.drawable.inv_ironhordeclefthoof
            )
        )
        mounts.add(
            Mount(
                616,
                "Shadowhide Pearltusk",
                WOD,
                "Garrison",
                0.0,
                WEEKLY,
                R.drawable.ability_mount_elekkdraenormount
            )
        )
        mounts.add(
            Mount(
                626,
                "Giant Coldsnout",
                WOD,
                "Garrison",
                0.0,
                WEEKLY,
                R.drawable.inv_giantboarmount_brown
            )
        )
        mounts.add(
            Mount(
                633,
                "Hellfire Infernal",
                LEGION,
                "The Nighthold",
                1.0,
                WEEKLY,
                R.drawable.inv_infernalmountred
            )
        )
        mounts.add(
            Mount(
                634,
                "Solar Spirehawk",
                WOD,
                "Spires of Arak",
                0.2,
                WEEKLY,
                R.drawable.inv_helm_suncrown_d_01
            )
        )
        mounts.add(
            Mount(
                642,
                "Garn Steelmaw",
                WOD,
                "Garrison",
                0.0,
                WEEKLY,
                R.drawable.inv_wolfdraenormountshadow
            )
        )
        mounts.add(
            Mount(
                649,
                "Smoky Direwolf",
                WOD,
                "Garrison",
                0.0,
                WEEKLY,
                R.drawable.inv_wolfdraenormountbrown
            )
        )
        mounts.add(
            Mount(
                751,
                "Felsteel Annihilator",
                WOD,
                "Hellfire Citadel",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_felreavermount
            )
        )
        mounts.add(
            Mount(
                791,
                "Felblaze Infernal",
                LEGION,
                "The Nighthold",
                1.0,
                WEEKLY,
                R.drawable.inv_infernalmount
            )
        )
        mounts.add(
            Mount(
                875,
                "Midnight",
                LEGION,
                "Return to Karazhan",
                1.0,
                WEEKLY,
                R.drawable.ability_mount_dreadsteed
            )
        )
        mounts.add(
            Mount(
                883,
                "Smoldering Ember Wyrm",
                LEGION,
                "Return to Karazhan",
                20.0,
                WEEKLY,
                R.drawable.inv_nightbane2mount
            )
        )
        mounts.add(
            Mount(
                899,
                "Abyss Worm",
                LEGION,
                "Tomb of Sargeras",
                1.0,
                WEEKLY,
                R.drawable.inv_serpentmount_green
            )
        )
        mounts.add(
            Mount(
                954,
                "Shackled Ur'zul",
                LEGION,
                "Antorus, the Burning Throne ",
                1.0,
                WEEKLY,
                R.drawable.inv_soulhoundmount_blue
            )
        )
        mounts.add(
            Mount(
                955,
                "Vile Fiend",
                LEGION,
                "Argus",
                3.0,
                DAILY,
                R.drawable.inv_argusfelstalkermount
            )
        )
        mounts.add(
            Mount(
                970,
                "Maddened Chaosrunner",
                LEGION,
                "Argus",
                3.0,
                DAILY,
                R.drawable.inv_argustalbukmount_felpurple
            )
        )
        mounts.add(
            Mount(
                971,
                "Antoran Charhound",
                LEGION,
                "Antorus, the Burning Throne ",
                1.0,
                WEEKLY,
                R.drawable.inv_felhound3_shadow_fire
            )
        )
        mounts.add(
            Mount(
                973,
                "Lambent Mana Ray",
                LEGION,
                "Argus",
                3.0,
                DAILY,
                R.drawable.inv_manaraymount_blue
            )
        )
        mounts.add(
            Mount(
                979,
                "Crimson Slavermaw",
                LEGION,
                "Argus",
                3.0,
                DAILY,
                R.drawable.inv_argusfelstalkermountred
            )
        )
        mounts.add(
            Mount(
                980,
                "Acid Belcher",
                LEGION,
                "Argus",
                3.0,
                DAILY,
                R.drawable.inv_argusfelstalkermountgrey
            )
        )
        mounts.add(
            Mount(
                981,
                "Biletooth Gnasher",
                LEGION,
                "Argus",
                3.0,
                DAILY,
                R.drawable.inv_argusfelstalkermountblue
            )
        )
        mounts.add(
            Mount(
                995,
                "Sharkbait",
                BFA,
                "Freehold",
                0.0,
                WEEKLY,
                R.drawable.inv_parrotmount_red
            )
        )
        mounts.add(
            Mount(
                1040,
                "Tomb Stalker",
                BFA,
                "King's Rest",
                0.0,
                WEEKLY,
                R.drawable.inv_armoredraptorundead
            )
        )
        mounts.add(
            Mount(
                1053,
                "Underrot Crawg",
                BFA,
                "Underrot",
                0.0,
                WEEKLY,
                R.drawable.inv_bloodtrollbeast_mount_pale
            )
        )
        mounts.add(
            Mount(
                1217,
                "G.M.O.D.",
                BFA,
                "Battle for Dazar'Alor",
                0.0,
                WEEKLY,
                R.drawable.achievement_dungeon_coinoperatedcrowdpummeler
            )
        )
        mounts.add(
            Mount(
                1219,
                "Glacial Tidestorm",
                BFA,
                "Battle for Dazar'Alor",
                0.0,
                WEEKLY,
                R.drawable.inv_waterelementalmount
            )
        )
        mounts.add(
            Mount(
                1227,
                "Aerial Unit R-21/X",
                BFA,
                "Operation: Mechagon",
                20.0,
                WEEKLY,
                R.drawable.inv_hunterkillershipyellow
            )
        )
        mounts.add(
            Mount(
                1229,
                "Rusty Mechanocrawler",
                BFA,
                "Mechagon",
                0.5,
                DAILY,
                R.drawable.inv_mechagonspidertank_junker
            )
        )
        mounts.add(
            Mount(
                1248,
                "Junkheap Drifter",
                BFA,
                "Mechagon",
                0.5,
                DAILY,
                R.drawable.inv_mechacyclejunk
            )
        )
        mounts.add(
            Mount(
                1250,
                "Mollie",
                BFA,
                "Voldun",
                0.0,
                WEEKLY,
                R.drawable.inv_alpacamount_brown
            )
        )
        mounts.add(
            Mount(
                1252,
                "Mechagon Peacekeeper",
                BFA,
                "Operation: Mechagon",
                0.0,
                WEEKLY,
                R.drawable.inv_mechagonspidertank_brass
            )
        )
        mounts.add(
            Mount(
                1293,
                "Ny'alotha Allseer",
                BFA,
                "Ny'alotha, the Waking City ",
                0.0,
                WEEKLY,
                R.drawable.inv_eyeballjellyfishmount
            )
        )
        mounts.add(
            Mount(
                1297,
                "Clutch of Ha-Li",
                BFA,
                "Vale of Eternal Blossoms",
                3.0,
                DAILY,
                R.drawable.inv_thunderislebirdbossmount_blue
            )
        )
        mounts.add(
            Mount(
                1317,
                "Waste Marauder",
                BFA,
                "Uldum",
                3.0,
                DAILY,
                R.drawable.inv_vulturemount_black
            )
        )
        mounts.add(
            Mount(
                1319,
                "Malevolent Drone",
                BFA,
                "Uldum",
                1.0,
                DAILY,
                R.drawable.inv_aqirflyingmount_black
            )
        )
        mounts.add(
            Mount(
                1327,
                "Ren's Stalwart Hound",
                BFA,
                "Vale of Eternal Blossoms",
                3.0,
                DAILY,
                R.drawable.inv_quilenmount_red
            )
        )
        mounts.add(
            Mount(
                1328,
                "Xinlao",
                BFA,
                "Vale of Eternal Blossoms",
                3.0,
                DAILY,
                R.drawable.inv_quilenmount_gold
            )
        )
        mounts.add(
            Mount(
                1298,
                "Hopecrusher Gargon",
                SL,
                "Revendreth",
                1.0,
                DAILY,
                R.drawable.inv_deathwargmountblack
            )
        )
        mounts.add(
            Mount(
                1304,
                "Mawsworn Soulhunter",
                SL,
                "The Maw",
                2.0,
                DAILY,
                R.drawable.inv_jailerhoundmount_gray
            )
        )
        mounts.add(
            Mount(
                1310,
                "Horrid Dredwing",
                SL,
                "Revendreth",
                2.0,
                DAILY,
                R.drawable.inv_giantvampirebatmount_bronze
            )
        )
        mounts.add(
            Mount(
                1362,
                "Spinemaw Gladechewer",
                SL,
                "Ardenweald",
                100.0,
                DAILY,
                R.drawable.inv_decomposermountyellow
            )
        )
        mounts.add(
            Mount(
                1366,
                "Bonehoof Tauralus",
                SL,
                "Maldraxxus",
                0.0,
                DAILY,
                R.drawable.inv_giantbeastmount
            )
        )
        mounts.add(
            Mount(
                1370,
                "Armored Bonehoof Tauralus",
                SL,
                "Maldraxxus",
                0.5,
                DAILY,
                R.drawable.inv_giantbeastmount2
            )
        )
        mounts.add(
            Mount(
                1372,
                "Blisterback Bloodtusk",
                SL,
                "Maldraxxus",
                2.0,
                DAILY,
                R.drawable.inv_maldraxxusboarmount_black
            )
        )
        mounts.add(
            Mount(
                1373,
                "Gorespine",
                SL,
                "Maldraxxus",
                2.0,
                DAILY,
                R.drawable.inv_maldraxxusboarmount_green
            )
        )
        mounts.add(
            Mount(
                1379,
                "Endmire Flyer",
                SL,
                "Revendreth",
                1.0,
                DAILY,
                R.drawable.inv_devourersmallmount_dark
            )
        )
        mounts.add(
            Mount(
                1393,
                "Wild Glimmerfur Prowler",
                SL,
                "Ardenweald",
                1.0,
                DAILY,
                R.drawable.inv_fox2_green
            )
        )
        mounts.add(
            Mount(
                1395,
                "Phalynx of Humility",
                SL,
                "Bastion",
                1.0,
                DAILY,
                R.drawable.inv_automatonlionmount_bronze
            )
        )
        mounts.add(
            Mount(
                1406,
                "Marrowfang",
                SL,
                "The Necrotic Wake",
                1.0,
                WEEKLY,
                R.drawable.inv_maldraxxusflyermount_red
            )
        )
        mounts.add(
            Mount(
                1410,
                "Hulking Deathroc",
                SL,
                "Maldraxxus",
                4.0,
                DAILY,
                R.drawable.inv_rocmaldraxxusmountpurple
            )
        )
        mounts.add(
            Mount(
                1411,
                "Predatory Plagueroc",
                SL,
                "Maldraxxus",
                2.0,
                DAILY,
                R.drawable.inv_rocmaldraxxusmountgreen
            )
        )
        mounts.add(
            Mount(
                1415,
                "Arboreal Gulper",
                SL,
                "Ardenweald",
                100.0,
                DAILY,
                R.drawable.inv_toadardenwealdmount
            )
        )
        mounts.add(
            Mount(
                1437,
                "Battle-Bound Warhound",
                SL,
                "Maldraxxus",
                1.0,
                DAILY,
                R.drawable.inv_darkhoundmount_draka
            )
        )
    }

    fun saveMountList(context: Context) {
        Log.v("MOUNTCOLLECTION", "Saving mounts...")
        val mountsToSave = ArrayList<Mount>()
        unknownMounts.forEach { mount ->
            mountsToSave.add(mount.clone())
        }
        val gson = Gson()
        val sharedPrefMount: SharedPreferences = context.getSharedPreferences("MOUNTS", 0)
        sharedPrefMount.edit().putString("MOUNTS", gson.toJson(mountsToSave)).apply()
    }

    fun getMounts(charPos: Int, fragment: Fragment) {
        Log.v("MOUNT", "In getMounts")
        val queue = Volley.newRequestQueue(fragment.activity)
        CharacterCollection.characters.forEach { character ->
            if (character.isMain) {
                var url = "https://" +
                        character.region +
                        ".api.blizzard.com/profile/wow/character/" +
                        character.realm.toLowerCase().replace("-", "").replace(" ", "-").replace("'", "") +
                        "/" +
                        character.name.toLowerCase() +
                        "/collections/mounts?namespace=profile-" +
                        character.region +
                        "&locale=en_GB&access_token=" +
                        AuthKey.token
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    { response ->
                        convertJson(response, fragment)
                        if (charPos >= 0) {
                            unknownMounts.forEach {
                                it.checkedList.add(charPos, false)
                                it.expanded = false
                            }
                            saveMountList(fragment.requireActivity())
                        }
                    },
                    {
                        Log.v("MOUNT", "GET didn't work!")
                        Log.v("MOUNT", it.stackTraceToString())
                    }
                )
                queue.add(stringRequest)
            }
        }
    }

    private fun convertJson(json: String, fragment: Fragment) {
        val gson = Gson()
        val mountContainer = gson.fromJson(json, MountContainer::class.java)
        if (unknownMounts.size == 0)
            initialMountFiltering(mountContainer, fragment)
        else
            updateMounts(mountContainer, fragment)

    }

    private fun initialMountFiltering(mountContainer: MountContainer, fragment: Fragment) {
        mounts.forEach { obtainable ->
            var found = false
            mountContainer.mounts.forEach { known ->
                if (known.mount.id == obtainable.id || (known.mount.id == 286 && obtainable.id == 287) || (known.mount.id == 287 && obtainable.id == 286)) {
                    found = true
                }
            }
            if (!found) {

                var foundInUnknown = false
                unknownMounts.forEach { unknownMount ->
                    if (unknownMount.id == obtainable.id) {
                        foundInUnknown = true
                    }
                }
                if (!foundInUnknown) {
                    unknownMounts.add(obtainable)
                }
            }
        }
        Log.v("MOUNT", "setFragmentResult")
        fragment.setFragmentResult("mountUpdate", Bundle())
        unknownMounts.sort()
        saveMountList(fragment.requireActivity())
    }

    private fun updateMounts(mountContainer: MountContainer, fragment: Fragment) {

        mounts.forEach { obtainable ->
            var unknown = false
            var mount = Mount()
            unknownMounts.forEach { unknownMount ->
                if (obtainable.id == unknownMount.id) {
                    unknown = true
                    mount = unknownMount
                    unknownMount.icon = obtainable.icon
                }
            }
            var known = false
            mountContainer.mounts.forEach { knownMount ->
                if (obtainable.id == knownMount.mount.id)
                    known = true
            }

            if (unknown && known) {
                var pos = unknownMounts.indexOf(mount)
                unknownMounts.remove(mount)
                val bundle = Bundle()
                bundle.putInt("mountPos", pos)
                fragment.setFragmentResult("removeMount", bundle)

            }
            if (!unknown && !known && obtainable.id != 286 && obtainable.id != 287) {
                CharacterCollection.characters.forEach {
                    obtainable.checkedList.add(false)
                }
                unknownMounts.add(obtainable)
                val bundle = Bundle()
                bundle.putInt("mountPos", unknownMounts.size - 1)
                fragment.setFragmentResult("addMount", bundle)
            }
        }
        unknownMounts.sort()
        fragment.setFragmentResult("mountUpdate", Bundle())

        saveMountList(fragment.requireActivity())
    }

}