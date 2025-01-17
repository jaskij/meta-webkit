require wpewebkit.inc
require conf/include/devupstream.inc

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI = "https://wpewebkit.org/releases/${BPN}-${PV}.tar.xz;name=tarball \
           file://0001-FELightningNEON.cpp-fails-to-build-NEON-fast-path-se.patch \
          "

SRC_URI[tarball.sha256sum] = "c6b25e168b70f2121305ed078d0790e0aa4b0c73fce44e32ed42d4e5dd137ccb"

DEPENDS += " libwpe"
RCONFLICTS:${PN} = "libwpe (< 1.12)"

SRC_URI:class-devupstream = "\
    git://github.com/WebKit/WebKit.git;protocol=https;branch=main \
"

# WPE 2.40.X branch was forked from the main branch in this commit
SRCREV:class-devupstream = "5b60400f474f318e45cd713638e365366b98dd87"

# documentation: Needed from 2.38
PACKAGECONFIG[documentation] = "-DENABLE_DOCUMENTATION=ON,-DENABLE_DOCUMENTATION=OFF, gi-docgen-native gi-docgen"

# introspection: Needed from 2.38
PACKAGECONFIG[introspection] = "-DENABLE_INTROSPECTION=ON,-DENABLE_INTROSPECTION=OFF, gobject-introspection-native"

# webgl2: Activated by default from >2.38
PACKAGECONFIG:append = " webgl2"

# TODO: documentation and introspection are disabled by default because the are
# causing cross-compiling build errors
# PACKAGECONFIG:append = " ${@bb.utils.contains('DISTRO_FEATURES', 'api-documentation', 'documentation', '' ,d)} introspection"

# Layer-Based SVG Engine
PACKAGECONFIG[lbse] = "-DENABLE_LAYER_BASED_SVG_ENGINE=ON,-DENABLE_LAYER_BASED_SVG_ENGINE=OFF, "

# unifdef-native: Needed since >2.38.
DEPENDS:append = " unifdef-native"
