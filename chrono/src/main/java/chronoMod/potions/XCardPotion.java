package chronoMod.potions;

import basemod.abstracts.CustomPotion;
import chronoMod.ChronoMod;
import chronoMod.actions.DiscoveryXAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class XCardPotion extends CustomPotion {
    public static final String POTION_ID = ChronoMod.makeID("XCardPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final Color LIQUID = CardHelper.getColor(153.0f, 0.0f, 153.0f);
    public static final Color HYBRID = CardHelper.getColor(255.0f, 0.0f, 255.0f);
    public static final Color SPOTS = CardHelper.getColor(255.0f, 0.0f, 255.0f);

    public XCardPotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.CARD, PotionColor.NONE);
        this.potency = this.getPotency();
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("SacredBark")) {
            this.description = potionStrings.DESCRIPTIONS[1];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.isThrown = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new DiscoveryXAction(this.potency));
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new XCardPotion();
    }

    public void upgradePotion()
    {
      this.potency += 1;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
