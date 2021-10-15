package chronoMod.potions;

import basemod.abstracts.CustomPotion;
import chronoMod.ChronoMod;
import chronoMod.actions.RepeatAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public class RepeatPotion extends CustomPotion {
    public static final String POTION_ID = ChronoMod.makeID("RepeatPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final Color LIQUID = CardHelper.getColor(0.0f, 0.0f, 153.0f);
    public static final Color HYBRID = CardHelper.getColor(0.0f, 255.0f, 255.0f);
    public static final Color SPOTS = CardHelper.getColor(0.0f, 255.0f, 255.0f);

    public RepeatPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionColor.NONE);
        this.potency = this.getPotency();
        if (this.potency == 1) {
            this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[2];
        }
        this.isThrown = false;
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        this.addToBot(new RepeatAction(this.potency));
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new RepeatPotion();
    }

    public void upgradePotion()
    {
      this.potency += 1;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
