#!/sbin/sh

# Remount system as R/W
mount -o rw,remount /system

# Add mapping for displayengine-hal-1.1
echo "(typeattributeset displayengineserver_27_0 (displayengineserver))" >> /system/etc/selinux/mapping/27.0.cil
echo "(expandtypeattribute (displayengineserver_27_0) true)" >> /system/etc/selinux/mapping/27.0.cil
echo "(typeattribute displayengineserver_27_0)" >> /system/etc/selinux/mapping/27.0.cil

# Fix logd service definition and SELinux for 8.0 vendor image
if [ "$(grep ro.build.version.release /vendor/build.prop)" = "ro.build.version.release=8.0.0" ]; then
    # Fix logd service definition
    sed -i "s/socket logdw dgram+passcred 0222 logd logd/socket logdw dgram 0222 logd logd/g" /system/etc/init/logd.rc

    # Add mapping for displayengine-hal-1.0
    echo "(typeattributeset displayengineserver_26_0 (displayengineserver))" >> /system/etc/selinux/mapping/26.0.cil
    echo "(typeattributeset displayengine_hwservice_26_0 (displayengine_hwservice))" >> /system/etc/selinux/mapping/26.0.cil

fi

    # Disable parsing intra-refresh-mode parameter in libstagefright
    sed -i 's/intra-refresh-mode/intra-refresh-nope/' /system/lib64/libstagefright.so
    sed -i 's/intra-refresh-mode/intra-refresh-nope/' /system/lib/libstagefright.so

exit 0
