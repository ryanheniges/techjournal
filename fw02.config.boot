interfaces {
    ethernet eth0 {
        address 10.0.17.54/24
        description SEC440-WAN
        duplex auto
        hw-id 00:50:56:a1:60:ff
        smp_affinity auto
        speed auto
        vrrp {
            vrrp-group 154 {
                advertise-interval 1
                preempt true
                priority 100
                virtual-address 10.0.17.74/24
            }
        }
    }
    ethernet eth1 {
        address 10.0.5.3/24
        description SEC440-LAN
        duplex auto
        hw-id 00:50:56:a1:53:17
        smp_affinity auto
        speed auto
        vrrp {
            vrrp-group 10 {
                advertise-interval 1
                preempt true
                priority 100
                virtual-address 10.0.5.1/24
            }
        }
    }
    ethernet eth2 {
        address 10.0.6.3/24
        description DMZ
        duplex auto
        hw-id 00:50:56:a1:c0:6f
        smp_affinity auto
        speed auto
        vrrp {
            vrrp-group 20 {
                advertise-interval 1
                preempt true
                priority 100
                virtual-address 10.0.6.1/24
            }
        }
    }
    loopback lo {
    }
}
nat {
    destination {
        rule 10 {
            description "Port Forwarding HTTP from WAN to Web01"
            destination {
                port 80
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 10.0.6.10
            }
        }
        rule 20 {
            description "Port Forwarding SSH from WAN to Web01"
            destination {
                port 22
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 10.0.5.100
            }
        }
    }
    source {
        rule 10 {
            description "NAT from LAN to WAN"
            outbound-interface eth0
            source {
                address 10.0.5.0/24
            }
            translation {
                address masquerade
            }
        }
        rule 20 {
            description "NAT FROM DMZ TO WAN"
            outbound-interface eth0
            source {
                address 10.0.6.0/24
            }
            translation {
                address masquerade
            }
        }
    }
}
service {
    dns {
        forwarding {
            cache-size 150
            listen-on eth1
        }
    }
}
system {
    config-management {
        commit-revisions 20
    }
    console {
    }
    gateway-address 10.0.17.2
    host-name fw02-ryan
    login {
        user deployer {
            authentication {
                encrypted-password $6$vH2t.BYSny.cpu$5Izlqp9WA08Ulp2.zvlwEH3lQfpuwP5E1uRK7GdJK0hN.qdmgc76QUqJsRFIKu/FNYeaM3/Khx0UVxznrSaEB.
                plaintext-password ""
            }
            level admin
        }
        user ryan {
            authentication {
                encrypted-password $6$VbyQgpbFsd8U5.V$GHlyHZGIDRQeIhO6ioHiFV5rubCznKqrF2QwlPwMW0fZM/lJdPXTWa/fxj/aP6B5wOMmiWQHDD44M2htnTrxL1
                plaintext-password ""
            }
            level admin
        }
    }
    name-server 10.0.17.2
    ntp {
        server 0.pool.ntp.org {
        }
        server 1.pool.ntp.org {
        }
        server 2.pool.ntp.org {
        }
    }
    package {
        auto-sync 1
        repository community {
            components main
            distribution helium
            password ""
            url http://packages.vyos.net/vyos
            username ""
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone UTC
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "system@6:conntrack-sync@1:webgui@1:webproxy@1:quagga@2:qos@1:dhcp-server@4:firewall@5:zone-policy@1:ipsec@4:wanloadbalance@3:conntrack@1:nat@4:config-management@1:dhcp-relay@1:vrrp@1:cron@1:cluster@1" === */
/* Release version: VyOS 1.1.8 */
