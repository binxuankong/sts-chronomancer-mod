package chronoMod.cards;

import chronoMod.patches.ChronoEnum;
import chronoMod.powers.*;
import chronoMod.relics.Grimoire;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public abstract class AbstractXCostCard extends AbstractDynamicCard {
    private static final int COST = -1;

    public AbstractXCostCard(final String id, final String img, final CardType type, final CardColor color,
                             final CardRarity rarity, final CardTarget target) {
        super(id, img, COST, type, color, rarity, target);
        this.tags.add(ChronoEnum.XCOST);
    }

    public int getEffectNum(AbstractPlayer p) {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        // Chemical X
        if (p.hasRelic("Chemical X")) {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        // Spell Boost
        AbstractPower spellBoost = p.getPower(SpellBoostPower.POWER_ID);
        if (spellBoost != null) {
            spellBoost.flash();
            effect += spellBoost.amount;
            this.addToBot(new RemoveSpecificPowerAction(p, p, spellBoost));
        }
        // Adept Spellcaster
        AbstractPower adeptSpellcaster = p.getPower(AdeptSpellcasterPower.POWER_ID);
        if (adeptSpellcaster != null) {
            adeptSpellcaster.flash();
            effect += adeptSpellcaster.amount;
        }
        // Use energy
        if (!this.freeToPlayOnce) {
            // Split Second
            if (EnergyPanel.totalCount >= 2) {
                this.addToBot(new ApplyPowerAction(p, p, new SplitSecondPower(p)));
            }
            // Temporal Paradox
            if (EnergyPanel.totalCount >= 3 && p.hasPower(TemporalParadoxPower.POWER_ID)) {
                p.getPower(TemporalParadoxPower.POWER_ID).onSpecificTrigger();
            }
            // Grimoire
            if (EnergyPanel.totalCount >= 1 && p.hasRelic(Grimoire.ID)) {
                p.getRelic(Grimoire.ID).onTrigger();
            }
            p.energy.use(EnergyPanel.totalCount);
        }
        return effect;
    }
}
