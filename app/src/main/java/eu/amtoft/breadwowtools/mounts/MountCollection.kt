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
import eu.amtoft.breadwowtools.api.MountContainer
import eu.amtoft.breadwowtools.characters.CharacterCollection

object MountCollection {
    var mounts: ArrayList<Mount> = ArrayList()
    var unknownMounts: ArrayList<Mount> = ArrayList()
    init {
        mounts.add(Mount(69,   "Rivendare's Deathcharger",        Expansion.VANILLA, "Stratholme",                   1.0,    Reset.WEEKLY, R.drawable.ability_mount_undeadhorse))
        mounts.add(Mount(117,  "Blue Qiraji Battle Tank",         Expansion.VANILLA, "Temple of Ahn'Qiraj",          0.0,    Reset.WEEKLY, R.drawable.inv_misc_qirajicrystal_04))
        mounts.add(Mount(118,  "Red Qiraji Battle Tank",          Expansion.VANILLA, "Temple of Ahn'Qiraj",          0.0,    Reset.WEEKLY, R.drawable.inv_misc_qirajicrystal_02))
        mounts.add(Mount(119,  "Yellow Qiraji Battle Tank",       Expansion.VANILLA, "Temple of Ahn'Qiraj",          0.0,    Reset.WEEKLY, R.drawable.inv_misc_qirajicrystal_01))
        mounts.add(Mount(120,  "Green Qiraji Battle Tank",        Expansion.VANILLA, "Temple of Ahn'Qiraj",          0.0,    Reset.WEEKLY, R.drawable.inv_misc_qirajicrystal_03))
        mounts.add(Mount(183,  "Ashes of Al'ar",                  Expansion.TBC,     "Tempest Keep",                 2.0,    Reset.WEEKLY, R.drawable.inv_misc_summerfest_brazierorange))
        mounts.add(Mount(168,  "Fiery Warhorse",                  Expansion.TBC,     "Karazhan",                     1.0,    Reset.WEEKLY, R.drawable.ability_mount_dreadsteed))
        mounts.add(Mount(185,  "Raven Lord",                      Expansion.TBC,     "Sethekk Halls",                1.0,    Reset.DAILY, R.drawable.inv_mount_raven_54))
        mounts.add(Mount(213,  "Swift White Hawkstrider",         Expansion.TBC,     "Magister's Terrace",           3.0,    Reset.DAILY, R.drawable.ability_mount_cockatricemountelite_white))
        mounts.add(Mount(237,  "White Polar Bear",                Expansion.WOTLK,   "The Storm Peaks",              3.0,    Reset.DAILY, R.drawable.ability_mount_polarbear_white))
        mounts.add(Mount(246,  "Azure Drake",                     Expansion.WOTLK,   "Eye of Eternity",              5.0,    Reset.WEEKLY, R.drawable.ability_mount_drake_azure))
        mounts.add(Mount(247,  "Blue Drake",                      Expansion.WOTLK,   "Eye of Eternity",              5.0,    Reset.WEEKLY, R.drawable.ability_mount_drake_azure))
        mounts.add(Mount(248,  "Bronze Drake",                    Expansion.WOTLK,   "The Culling of Stratholme",    100.0,  Reset.DAILY, R.drawable.ability_mount_drake_bronze))
        mounts.add(Mount(250,  "Twilight Drake",                  Expansion.WOTLK,   "The Obsidian Sanctum",         100.0,  Reset.WEEKLY, R.drawable.ability_mount_drake_twilight))
        mounts.add(Mount(253,  "Black Drake",                     Expansion.WOTLK,   "The Obsidian Sanctum",         100.0,  Reset.WEEKLY, R.drawable.ability_mount_drake_twilight))
        mounts.add(Mount(264,  "Blue Proto-Drake",                Expansion.WOTLK,   "Utgarde Pinnacle",             1.0,    Reset.DAILY, R.drawable.ability_mount_drake_proto))
        mounts.add(Mount(286,  "Grand Black War Mammoth",         Expansion.WOTLK,   "Vault of Archavon",            1.0,    Reset.WEEKLY, R.drawable.ability_mount_mammoth_black_3seater))
        mounts.add(Mount(287,  "Grand Black War Mammoth",         Expansion.WOTLK,   "Vault of Archavon",            1.0,    Reset.WEEKLY, R.drawable.ability_mount_mammoth_black_3seater))
        mounts.add(Mount(304,  "Mimiron's Head",                  Expansion.WOTLK,   "Ulduar",                       1.0,    Reset.WEEKLY, R.drawable.inv_misc_enggizmos_03))
        mounts.add(Mount(349,  "Onyxian Drake",                   Expansion.WOTLK,   "Onyxia's Lair",                1.0,    Reset.WEEKLY, R.drawable.achievement_boss_onyxia))
        mounts.add(Mount(363,  "Invincible",                      Expansion.WOTLK,   "Icecrown Citadel",             1.0,    Reset.WEEKLY, R.drawable.ability_mount_pegasus))
        mounts.add(Mount(395,  "Drake of the North Wind",         Expansion.CATA,    "Vortex Pinnacle",              1.0,    Reset.DAILY, R.drawable.inv_misc_stormdragonpale))
        mounts.add(Mount(396,  "Drake of the South Wind",         Expansion.CATA,    "Throne of the Four Winds",     1.0,    Reset.WEEKLY, R.drawable.inv_misc_stormdragonpurple))
        mounts.add(Mount(397,  "Vitreous Stone Drake",            Expansion.CATA,    "Stonecore",                    1.0,    Reset.DAILY, R.drawable.inv_misc_stonedragonblue))
        mounts.add(Mount(410,  "Armored Razzashi Raptor",         Expansion.CATA,    "Zul'Gurub",                    1.0,    Reset.DAILY, R.drawable.ability_mount_raptor))
        mounts.add(Mount(411,  "Swift Zulian Panther",            Expansion.CATA,    "Zul'Gurub",                    1.0,    Reset.DAILY, R.drawable.ability_mount_blackpanther))
        mounts.add(Mount(415,  "Pureblood Fire Hawk",             Expansion.CATA,    "Firelands",                    1.0,    Reset.WEEKLY, R.drawable.inv_misc_orb_05))
        mounts.add(Mount(419,  "Amani Battle Bear",               Expansion.CATA,    "Zul'Aman",                     100.0,  Reset.DAILY, R.drawable.ability_druid_challangingroar))
        mounts.add(Mount(425,  "Flametalon of Alysrazor",         Expansion.CATA,    "Firelands",                    2.0,    Reset.WEEKLY, R.drawable.ability_mount_fireravengodmount))
        mounts.add(Mount(442,  "Blazing Drake",                   Expansion.CATA,    "Dragon Soul",                  3.0,    Reset.WEEKLY, R.drawable.ability_mount_drake_red))
        mounts.add(Mount(444,  "Life-Binder's Handmaiden",        Expansion.CATA,    "Dragon Soul",                  1.0,    Reset.WEEKLY, R.drawable.ability_mount_drake_red))
        mounts.add(Mount(445,  "Experiment 12-B",                 Expansion.CATA,    "Dragon Soul",                  1.0,    Reset.WEEKLY, R.drawable.ability_mount_drake_twilight))
        mounts.add(Mount(473,  "Heavenly Onyx Cloud Serpent",     Expansion.MOP,     "Kun-Lai Summit",               0.1,    Reset.WEEKLY, R.drawable.inv_pandarenserpentgodmount_black))
        mounts.add(Mount(478,  "Astral Cloud Serpent",            Expansion.MOP,     "Mogu'shan Vaults",             1.0,    Reset.WEEKLY, R.drawable.inv_celestialserpentmount))
        mounts.add(Mount(515,  "Son of Galleon",                  Expansion.MOP,     "Valley of the Four Winds",     0.1,    Reset.WEEKLY, R.drawable.inv_mushanbeastmount))
        mounts.add(Mount(531,  "Spawn of Horridon",               Expansion.MOP,     "Throne of Thunder",            1.0,    Reset.WEEKLY, R.drawable.ability_mount_triceratopsmount))
        mounts.add(Mount(533,  "Cobalt Primordial Direhorn",      Expansion.MOP,     "Isle of Giants",               0.1,    Reset.WEEKLY, R.drawable.ability_mount_triceratopsmount_blue))
        mounts.add(Mount(542,  "Thundering Cobalt Cloud Serpent", Expansion.MOP,     "Isle of Thunder",              0.1,    Reset.WEEKLY, R.drawable.inv_pandarenserpentmount_lightning_blue))
        mounts.add(Mount(543,  "Clutch of Ji-Kun",                Expansion.MOP,     "Throne of Thunder",            1.0,    Reset.WEEKLY, R.drawable.achievement_boss_ji_kun))
        mounts.add(Mount(559,  "Kor'kron Juggernaut",             Expansion.MOP,     "Siege of Orgrimmar",           1.0,    Reset.WEEKLY, R.drawable.inv_mount_hordescorpiong))
        mounts.add(Mount(613,  "Ironhoof Destroyer",              Expansion.WOD,     "Blackrock Foundry",            1.0,    Reset.WEEKLY, R.drawable.inv_ironhordeclefthoof))
        mounts.add(Mount(616,  "Shadowhide Pearltusk",            Expansion.WOD,     "Garrison",                     0.0,    Reset.WEEKLY, R.drawable.ability_mount_elekkdraenormount))
        mounts.add(Mount(626,  "Giant Coldsnout",                 Expansion.WOD,     "Garrison",                     0.0,    Reset.WEEKLY, R.drawable.inv_giantboarmount_brown))
        mounts.add(Mount(633,  "Hellfire Infernal",               Expansion.LEGION,  "The Nighthold",                1.0,    Reset.WEEKLY, R.drawable.inv_infernalmountred))
        mounts.add(Mount(634,  "Solar Spirehawk",                 Expansion.WOD,     "Spires of Arak",               0.2,    Reset.WEEKLY, R.drawable.inv_helm_suncrown_d_01))
        mounts.add(Mount(642,  "Garn Steelmaw",                   Expansion.WOD,     "Garrison",                     0.0,    Reset.WEEKLY, R.drawable.inv_wolfdraenormountshadow))
        mounts.add(Mount(649,  "Smoky Direwolf",                  Expansion.WOD,     "Garrison",                     0.0,    Reset.WEEKLY, R.drawable.inv_wolfdraenormountbrown))
        mounts.add(Mount(751,  "Felsteel Annihilator",            Expansion.WOD,     "Hellfire Citadel",             1.0,    Reset.WEEKLY, R.drawable.ability_mount_felreavermount))
        mounts.add(Mount(791,  "Felblaze Infernal",               Expansion.LEGION,  "The Nighthold",                1.0,    Reset.WEEKLY, R.drawable.inv_infernalmount))
        mounts.add(Mount(875,  "Midnight",                        Expansion.LEGION,  "Return to Karazhan",           1.0,    Reset.WEEKLY, R.drawable.ability_mount_dreadsteed))
        mounts.add(Mount(883,  "Smoldering Ember Wyrm",           Expansion.LEGION,  "Return to Karazhan",           20.0,   Reset.WEEKLY, R.drawable.inv_nightbane2mount))
        mounts.add(Mount(899,  "Abyss Worm",                      Expansion.LEGION,  "Tomb of Sargeras",             1.0,    Reset.WEEKLY, R.drawable.inv_serpentmount_green))
        mounts.add(Mount(954,  "Shackled Ur'zul",                 Expansion.LEGION,  "Antorus, the Burning Throne",  1.0,    Reset.WEEKLY, R.drawable.inv_soulhoundmount_blue))
        mounts.add(Mount(955,  "Vile Fiend",                      Expansion.LEGION,  "Argus",                        3.0,    Reset.DAILY, R.drawable.inv_argusfelstalkermount))
        mounts.add(Mount(970,  "Maddened Chaosrunner",            Expansion.LEGION,  "Argus",                        3.0,    Reset.DAILY, R.drawable.inv_argustalbukmount_felpurple))
        mounts.add(Mount(971,  "Antoran Charhound",               Expansion.LEGION,  "Antorus, the Burning Throne",  1.0,    Reset.WEEKLY, R.drawable.inv_felhound3_shadow_fire))
        mounts.add(Mount(973,  "Lambent Mana Ray",                Expansion.LEGION,  "Argus",                        3.0,    Reset.DAILY, R.drawable.inv_manaraymount_blue))
        mounts.add(Mount(979,  "Crimson Slavermaw",               Expansion.LEGION,  "Argus",                        3.0,    Reset.DAILY, R.drawable.inv_argusfelstalkermountred))
        mounts.add(Mount(980,  "Acid Belcher",                    Expansion.LEGION,  "Argus",                        3.0,    Reset.DAILY, R.drawable.inv_argusfelstalkermountgrey))
        mounts.add(Mount(981,  "Biletooth Gnasher",               Expansion.LEGION,  "Argus",                        3.0,    Reset.DAILY, R.drawable.inv_argusfelstalkermountblue))
        mounts.add(Mount(995,  "Sharkbait",                       Expansion.BFA,     "Freehold",                     0.0,    Reset.WEEKLY, R.drawable.inv_parrotmount_red))
        mounts.add(Mount(1040, "Tomb Stalker",                    Expansion.BFA,     "King's Rest",                  0.0,    Reset.WEEKLY, R.drawable.inv_armoredraptorundead))
        mounts.add(Mount(1053, "Underrot Crawg",                  Expansion.BFA,     "Underrot",                     0.0,    Reset.WEEKLY, R.drawable.inv_bloodtrollbeast_mount_pale))
        mounts.add(Mount(1217, "G.M.O.D.",                        Expansion.BFA,     "Battle for Dazar'Alor",        0.0,    Reset.WEEKLY, R.drawable.achievement_dungeon_coinoperatedcrowdpummeler))
        mounts.add(Mount(1219, "Glacial Tidestorm",               Expansion.BFA,     "Battle for Dazar'Alor",        0.0,    Reset.WEEKLY, R.drawable.inv_waterelementalmount))
        mounts.add(Mount(1227, "Aerial Unit R-21/X",              Expansion.BFA,     "Operation: Mechagon",          20.0,   Reset.WEEKLY, R.drawable.inv_hunterkillershipyellow))
        mounts.add(Mount(1229, "Rusty Mechanocrawler",            Expansion.BFA,     "Mechagon",                     0.5,    Reset.DAILY, R.drawable.inv_mechagonspidertank_junker))
        mounts.add(Mount(1248, "Junkheap Drifter",                Expansion.BFA,     "Mechagon",                     0.5,    Reset.DAILY, R.drawable.inv_mechacyclejunk))
        mounts.add(Mount(1250, "Mollie",                          Expansion.BFA,     "Voldun",                       0.0,    Reset.WEEKLY, R.drawable.inv_alpacamount_brown))
        mounts.add(Mount(1252, "Mechagon Peacekeeper",            Expansion.BFA,     "Operation: Mechagon",          0.0,    Reset.WEEKLY, R.drawable.inv_mechagonspidertank_brass))
        mounts.add(Mount(1293, "Ny'alotha Allseer",               Expansion.BFA,     "Ny'alotha, the Waking City",   0.0,    Reset.WEEKLY, R.drawable.inv_eyeballjellyfishmount))
        mounts.add(Mount(1297, "Clutch of Ha-Li",                 Expansion.BFA,     "Vale of Eternal Blossoms",     3.0,    Reset.DAILY, R.drawable.inv_thunderislebirdbossmount_blue))
        mounts.add(Mount(1317, "Waste Marauder",                  Expansion.BFA,     "Uldum",                        3.0,    Reset.DAILY, R.drawable.inv_vulturemount_black))
        mounts.add(Mount(1319, "Malevolent Drone",                Expansion.BFA,     "Uldum",                        1.0,    Reset.DAILY, R.drawable.inv_aqirflyingmount_black))
        mounts.add(Mount(1327, "Ren's Stalwart Hound",            Expansion.BFA,     "Vale of Eternal Blossoms",     3.0,    Reset.DAILY, R.drawable.inv_quilenmount_red))
        mounts.add(Mount(1328, "Xinlao",                          Expansion.BFA,     "Vale of Eternal Blossoms",     3.0,    Reset.DAILY, R.drawable.inv_quilenmount_gold))
    }

    fun saveMountList(context: Context){
        Log.v("MOUNTCOLLECTION", "Saving mounts...")
        val mountsToSave = ArrayList<Mount>()
        unknownMounts.forEach{ mount ->
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
                            }
                            saveMountList(fragment.requireActivity())
                        }
                    },
                    {
                        Log.v("MOUNT", "GET didn't work!")
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