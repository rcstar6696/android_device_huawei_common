#!/system/bin/sh
set -e

if mount -o remount,rw /system;then
	resize2fs $(grep ' /system ' /proc/mounts |cut -d ' ' -f 1) || true
elif mount -o remount,rw /;then
	resize2fs /dev/root || true
fi

for i in lib lib64 ; do
	if [ -f /odm/$i/liboemcrypto.so ]; then ln -sf /odm/$i/liboemcrypto.so /system/$i; fi
done

mount -o remount,ro /system || true
mount -o remount,ro / || true

if ! grep android.hardware.ir /vendor/manifest.xml;then
    mount -o bind /dev/null /system/etc/permissions/android.hardware.consumerir.xml
fi
