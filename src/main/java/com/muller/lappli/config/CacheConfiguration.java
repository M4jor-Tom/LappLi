package com.muller.lappli.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.muller.lappli.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.muller.lappli.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.muller.lappli.domain.User.class.getName());
            createCache(cm, com.muller.lappli.domain.Authority.class.getName());
            createCache(cm, com.muller.lappli.domain.User.class.getName() + ".authorities");
            createCache(cm, com.muller.lappli.domain.Element.class.getName());
            createCache(cm, com.muller.lappli.domain.ElementKind.class.getName());
            createCache(cm, com.muller.lappli.domain.Copper.class.getName());
            createCache(cm, com.muller.lappli.domain.Material.class.getName());
            createCache(cm, com.muller.lappli.domain.ElementSupply.class.getName());
            createCache(cm, com.muller.lappli.domain.Lifter.class.getName());
            createCache(cm, com.muller.lappli.domain.LifterRunMeasure.class.getName());
            createCache(cm, com.muller.lappli.domain.BangleSupply.class.getName());
            createCache(cm, com.muller.lappli.domain.Bangle.class.getName());
            createCache(cm, com.muller.lappli.domain.ElementKindEdition.class.getName());
            createCache(cm, com.muller.lappli.domain.Material.class.getName() + ".materialMarkingStatistics");
            createCache(cm, com.muller.lappli.domain.MaterialMarkingStatistic.class.getName());
            createCache(cm, com.muller.lappli.domain.CustomComponentSupply.class.getName());
            createCache(cm, com.muller.lappli.domain.CustomComponent.class.getName());
            createCache(cm, com.muller.lappli.domain.Strand.class.getName());
            createCache(cm, com.muller.lappli.domain.OneStudySupply.class.getName());
            createCache(cm, com.muller.lappli.domain.Strand.class.getName() + ".elementSupplies");
            createCache(cm, com.muller.lappli.domain.Strand.class.getName() + ".bangleSupplies");
            createCache(cm, com.muller.lappli.domain.Strand.class.getName() + ".customComponentSupplies");
            createCache(cm, com.muller.lappli.domain.Strand.class.getName() + ".oneStudySupplies");
            createCache(cm, com.muller.lappli.domain.Study.class.getName());
            createCache(cm, com.muller.lappli.domain.Study.class.getName() + ".strandSupplies");
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName());
            createCache(cm, com.muller.lappli.domain.UserData.class.getName());
            createCache(cm, com.muller.lappli.domain.UserData.class.getName() + ".studies");
            createCache(cm, com.muller.lappli.domain.CentralAssembly.class.getName());
            createCache(cm, com.muller.lappli.domain.CoreAssembly.class.getName());
            createCache(cm, com.muller.lappli.domain.IntersticeAssembly.class.getName());
            createCache(cm, com.muller.lappli.domain.Study.class.getName() + ".strands");
            createCache(cm, com.muller.lappli.domain.Sheathing.class.getName());
            createCache(cm, com.muller.lappli.domain.ElementSupply.class.getName() + ".ownerSupplyPositions");
            createCache(cm, com.muller.lappli.domain.BangleSupply.class.getName() + ".ownerSupplyPositions");
            createCache(cm, com.muller.lappli.domain.CustomComponentSupply.class.getName() + ".ownerSupplyPositions");
            createCache(cm, com.muller.lappli.domain.Strand.class.getName() + ".supplyPositions");
            createCache(cm, com.muller.lappli.domain.OneStudySupply.class.getName() + ".ownerSupplyPositions");
            createCache(cm, com.muller.lappli.domain.IntersticeAssembly.class.getName() + ".supplyPositions");
            createCache(cm, com.muller.lappli.domain.SupplyPosition.class.getName());
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".coreAssemblies");
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".intersticeAssemblies");
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".sheathings");
            createCache(cm, com.muller.lappli.domain.Tape.class.getName());
            createCache(cm, com.muller.lappli.domain.TapeKind.class.getName());
            createCache(cm, com.muller.lappli.domain.TapeLaying.class.getName());
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".tapeLayings");
            createCache(cm, com.muller.lappli.domain.Screen.class.getName());
            createCache(cm, com.muller.lappli.domain.CopperFiber.class.getName());
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".screens");
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".stripLayings");
            createCache(cm, com.muller.lappli.domain.Strip.class.getName());
            createCache(cm, com.muller.lappli.domain.StripLaying.class.getName());
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".continuityWireLongitLayings");
            createCache(cm, com.muller.lappli.domain.ContinuityWireLongitLaying.class.getName());
            createCache(cm, com.muller.lappli.domain.ContinuityWire.class.getName());
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".plaits");
            createCache(cm, com.muller.lappli.domain.Plait.class.getName());
            createCache(cm, com.muller.lappli.domain.SteelFiber.class.getName());
            createCache(cm, com.muller.lappli.domain.StrandSupply.class.getName() + ".carrierPlaits");
            createCache(cm, com.muller.lappli.domain.CarrierPlait.class.getName());
            createCache(cm, com.muller.lappli.domain.CarrierPlaitFiber.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
